package com.earth.entity

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.annotation.Id

@Document(collection="Product")
data class ProductEntity(
        @Id
        @Field
        val ProductID: String,
        val Name: String,
        val Price: Double,
        val StockQuantity: Int,
        val Description: String?,
        val Category: String?,
        val CustomerID: String
)       