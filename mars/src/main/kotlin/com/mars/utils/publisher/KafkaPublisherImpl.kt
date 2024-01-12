package com.mars.utils.publisher

// import com.mars.controller.ProductController
import com.mars.utils.SerializationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import io.github.oshai.kotlinlogging.KotlinLogging

@Service
class KafkaPublisherImpl(private val kafkaTemplate: KafkaTemplate<String, ByteArray>) : KafkaPublisher {
    private val logger = KotlinLogging.logger {}

    override fun publish(topicName: String, data: Any) {
        try {
            val jsonBytes = SerializationUtils.serializeToJsonBytes(data)
            val record = ProducerRecord<String, ByteArray>(topicName, jsonBytes)
            kafkaTemplate.send(record).get(timeoutMillis, TimeUnit.MILLISECONDS)
            logger.info("publishing kafka record value >>>>> ${String(record.value())} to topic >>>> $topicName")
        } catch (ex: Exception) {
            logger.error("KafkaPublisherImpl publish", ex)
            throw RuntimeException("KafkaPublisherImpl publish", ex)
        }
    }

    companion object {
        private const val timeoutMillis = 5000L
    }
}