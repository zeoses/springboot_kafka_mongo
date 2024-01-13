package com.earth.repository

import com.earth.entity.ProductEntity
import com.earth.domain.Product
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.BulkOperations.BulkMode
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.LinkedBlockingDeque


@Repository
class ProductRepository(private val mongoTemplate: MongoTemplate) {

    // Bulk add
    fun saveAll(products: LinkedBlockingDeque<Product>): List<Product> {
        val bulkOperations: BulkOperations = mongoTemplate.bulkOps(BulkMode.ORDERED, ProductEntity::class.java)

        for (product in products) {
            bulkOperations.insert(product)
        }

        return products.toList()
    }

    // Add
    fun save(product: ProductEntity): ProductEntity {
        return mongoTemplate.insert(product)
    }

    // Read
    fun findByProductID(productID: String): ProductEntity? {
        return mongoTemplate.findById(productID, ProductEntity::class.java)
    }

    // // Update
    // fun updateByProductID(productID: String, updatedProduct: ProductEntity): ProductEntity? {
    //     var existingProduct = findByProductID(productID)
    //     return if (existingProduct != null) {
    //         existingProduct.Name = updatedProduct.Name // Ensure the ID remains the same
    //         mongoTemplate.save(updatedProduct)
    //         updatedProduct
    //     } else {
    //         null
    //     }
    // }

    // Delete
    fun deleteByProductID(productID: String): Boolean {
        val query = org.springframework.data.mongodb.core.query.Query()
            .addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("ProductID").`is`(productID))
        return mongoTemplate.remove(query, ProductEntity::class.java).wasAcknowledged()
    }
}
