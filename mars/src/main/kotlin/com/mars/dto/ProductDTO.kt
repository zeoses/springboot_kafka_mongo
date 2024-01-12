package com.mars.dto


data class ProductDTO (
    val ProductID : String,
    val Name: String,
    val Price: Double,
    val StockQuantity: Int,
    val Description: String?,
    val Category: String,
    val CustomerID: String,
)
