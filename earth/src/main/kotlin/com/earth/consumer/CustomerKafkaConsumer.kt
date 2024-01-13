package com.earth.consumer

import com.earth.domain.Customer
// import com.earth.entity.CusromerEntity
import com.earth.exceptions.SerializationException
import com.earth.repository.CusromerRepository
import com.earth.utils.SerializationUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.LinkedBlockingDeque
import jakarta.annotation.PreDestroy
import io.github.oshai.kotlinlogging.KotlinLogging



@Service
class CustomerKafkaConsumer(private val cusromerRepository: CusromerRepository) {
    private val batchQueue = LinkedBlockingDeque<Customer>()
    private val mutex = Mutex()

    @Value(value = "\${spring.kafka.consumer.batch-queue-size}")
    private val batchQueueSize: Int = 500

    @KafkaListener(
        topics = ["\${kafkatopic1}"], groupId = "\${earth.kafka.groupId}", concurrency = "\${earth.kafka.default-concurrency}"
    )
    fun processIndexProduct(@Payload data: ByteArray, ack: Acknowledgment) {
        try {
            val cusromer = SerializationUtils.deserializeFromJsonBytes(data, Customer::class.java)
            handleBatchIndex(cusromer)
            ack.acknowledge().also {
                log.info("<<<commit>>> process index for CusromerID: ${cusromer.CustomerID}")
            }
        } catch (ex: SerializationException) {
            ack.acknowledge().also { log.error("<<<commit error>>> serialization error", ex) }
        } catch (ex: Exception) {
            log.error("processIndexProduct Exception", ex)
        } finally {
            log.info("finish")
        }
    }

    private fun handleBatchIndex(cusromer: Customer) {
        try {
            batchQueue.add(cusromer)
            if (batchQueue.size >= batchQueueSize) cusromerRepository.saveAll(batchQueue).also {
                log.info("(handleBatchIndex) saved queueSize: >>>>>>>>>>>>>> ${batchQueue.size}")
                batchQueue.clear()
            }
        } finally {
            log.info("finish")
        }
    }


    @Scheduled(initialDelay = 25000, fixedRate = 10000)
    fun flushBulkInsert(){
            log.info("(Scheduled) running scheduled insert queueSize: ${batchQueue.size} \n")
            try {
                if (batchQueue.isNotEmpty()) cusromerRepository.saveAll(batchQueue).also {
                    log.info("(Scheduled) saved queueSize: ${batchQueue.size} \n\n\n\n")
                    batchQueue.clear()
                }
            } catch (ex: Exception) {
                throw ex
            } finally {
                log.info("finish")
            }
    }

    @PreDestroy
    fun flushBatchQueue() = runBlocking {
        try {
            if (batchQueue.isNotEmpty()) cusromerRepository.saveAll(batchQueue)
                .also { batchQueue.clear().also { log.info("batch queue saved") } }
        } catch (ex: Exception) {
            log.error("flushBatchQueue", ex)
            throw ex
        } finally {
            log.info("finish")
        }
    }

    companion object {
        private val log = KotlinLogging.logger {}
        private const val handleTimeoutMillis = 5000L
        private val errorhandler = CoroutineExceptionHandler { _, throwable ->
            run { log.error("(ProductKafkaConsumer) CoroutineExceptionHandler", throwable) }
        }
    }
}
