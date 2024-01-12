package com.mars.service

import com.mars.domain.Product
import com.mars.utils.publisher.KafkaPublisher

import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class ProductService(
    private val publisher: KafkaPublisher,
    @Value(value = "\${kafkatopic2:products}")
    private val CustomerTopicName: String,
){
    fun index(product: Product){
        try {
            publisher.publish(topicName=CustomerTopicName, data=product)
        } finally {
            println("finfish")
        }
    }
}