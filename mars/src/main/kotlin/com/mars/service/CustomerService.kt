package com.mars.service

import com.mars.domain.Customer
import com.mars.utils.publisher.KafkaPublisher

import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class CustomerService(
    private val publisher: KafkaPublisher,
    @Value(value = "\${kafkatopic1:customers}")
    private val CustomerTopicName: String,
){
    fun index(customer: Customer){
        try {
            publisher.publish(topicName=CustomerTopicName, data=customer)
        } finally {
            println("finfish")
        }
    }
}