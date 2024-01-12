package com.mars.controller

import com.mars.dto.CustomerDTO
import com.mars.domain.Customer
import com.mars.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlinx.coroutines.withTimeout
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import io.github.oshai.kotlinlogging.KotlinLogging

@RestController
@RequestMapping("/api/v1/customer")
@Validated
class CustomerController(private val coustomerService :CustomerService) {
    
    @PostMapping
    fun index(@Valid @RequestBody request: CustomerDTO): ResponseEntity<*> {
        try{
            val customer = Customer.of(request)
            coustomerService.index(customer)
            return ResponseEntity<Customer>(customer, HttpStatus.CREATED);
        }catch (ex: Exception) {
            logger.error("error ", ex) 
            return ResponseEntity<String>("Something go Wring: $ex", HttpStatus.NOT_ACCEPTABLE);
        } 
    }

    @PutMapping
    fun update(@Valid @RequestBody request: CustomerDTO): ResponseEntity<*> {
        try{
            val customer = Customer.of(request)
            coustomerService.index(customer)
            return ResponseEntity<Customer>(customer, HttpStatus.OK);
        }catch (ex: Exception) {
            logger.error("error ", ex) 
            return ResponseEntity<String>("Something go Wring: $ex", HttpStatus.NOT_ACCEPTABLE);
        } 
    }
    
    companion object {
        private val logger = KotlinLogging.logger {}
        private const val timeoutMillis = 5000L
    }
}