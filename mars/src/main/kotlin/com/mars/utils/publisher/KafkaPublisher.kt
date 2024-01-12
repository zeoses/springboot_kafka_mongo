package com.mars.utils.publisher

interface KafkaPublisher {
    fun publish(topicName: String, data: Any)
}