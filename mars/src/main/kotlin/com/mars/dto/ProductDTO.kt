package com.mars.dto


data class ProductDTO (
    val ProductID : String,
    val Name: String,
    val Price: Double = 0.0,
    val StockQuantity: Int = 0,
    val Description: String?,
    val Category: String,
    val CustomerID: String,
)
