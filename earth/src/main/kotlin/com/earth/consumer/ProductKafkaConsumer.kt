package com.earth.consumer

// import com.earth.entity.Product
import com.earth.domain.Product
import com.earth.exceptions.SerializationException
import com.earth.repository.ProductRepository
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
class ProductKafkaConsumer(private val productRepository: ProductRepository) {
    private val batchQueue = LinkedBlockingDeque<Product>()
    private val mutex = Mutex()

    @Value(value = "\${spring.kafka.consumer.batch-queue-size}")
    private val batchQueueSize: Int = 500

    @KafkaListener(
        topics = ["\${kafkatopic2}"], groupId = "\${earth.kafka.groupId}", concurrency = "\${earth.kafka.default-concurrency}"
    )
    fun processIndexProduct(@Payload data: ByteArray, ack: Acknowledgment) {
        try {
            val product = SerializationUtils.deserializeFromJsonBytes(data, Product::class.java)
            handleBatchIndex(product)
            ack.acknowledge().also {
                log.info("<<<commit>>> process index for productID: ${product.ProductID}")
            }
        } catch (ex: SerializationException) {
            ack.acknowledge().also { log.error("<<<commit error>>> serialization error", ex) }
        } catch (ex: Exception) {
            log.error("processIndexProduct Exception", ex)
        } finally {
            log.info("finish")
        }
    }

    private fun handleBatchIndex(product: Product) {
        try {
            batchQueue.add(product)
            if (batchQueue.size >= batchQueueSize) productRepository.saveAll(batchQueue).also {
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
                if (batchQueue.isNotEmpty()) productRepository.saveAll(batchQueue).also {
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
            if (batchQueue.isNotEmpty()) productRepository.saveAll(batchQueue)
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
