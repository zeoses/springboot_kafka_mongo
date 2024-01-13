package com.earth.repository

import com.earth.entity.CusromerEntity
import com.earth.domain.Customer
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.BulkOperations.BulkMode
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.LinkedBlockingDeque

@Repository
class CusromerRepository(private val mongoTemplate: MongoTemplate) {

    // Bulk add
    fun saveAll(products: LinkedBlockingDeque<Customer>): List<Customer> {
        val bulkOperations: BulkOperations = mongoTemplate.bulkOps(BulkMode.ORDERED, CusromerEntity::class.java)

        for (product in products) {
            bulkOperations.insert(product)
        }

        return products.toList()
    }

    // Add
    fun save(product: CusromerEntity): CusromerEntity {
        return mongoTemplate.insert(product)
    }

    // Read
    fun findByProductID(productID: String): CusromerEntity? {
        return mongoTemplate.findById(productID, CusromerEntity::class.java)
    }

    // // Update
    // fun updateByProductID(productID: String, updatedProduct: CusromerEntity): CusromerEntity? {
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
        return mongoTemplate.remove(query, CusromerEntity::class.java).wasAcknowledged()
    }
}
