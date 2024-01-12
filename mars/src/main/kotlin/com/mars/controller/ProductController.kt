package com.mars.controller

import com.mars.dto.ProductDTO
import com.mars.domain.Product
import com.mars.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlinx.coroutines.withTimeout
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import io.github.oshai.kotlinlogging.KotlinLogging

@RestController
@RequestMapping("/api/v1/product")
@Validated
class ProductController(private val productService :ProductService) {
    
    @PostMapping
    fun index(@Valid @RequestBody request: ProductDTO): ResponseEntity<*> {
        try{
            val product = Product.of(request)
            productService.index(product)
            return ResponseEntity<Product>(product, HttpStatus.CREATED);
        }catch (ex: Exception) {
            logger.error("error ", ex) 
            return ResponseEntity<String>("Something go Wring: $ex", HttpStatus.NOT_ACCEPTABLE);
        } 
    }

    @PutMapping
    fun update(@Valid @RequestBody request: ProductDTO): ResponseEntity<*> {
        try{
            val product = Product.of(request)
            productService.index(product)
            return ResponseEntity<Product>(product, HttpStatus.OK);
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