package com.mars.dto
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class ProductDTO (
    @get:Size(min = 4, max = 60) val ProductID : String,
    val Name: String,
    @get:Min(0) val Price: Double = 0.0,
    @get:Min(0) val StockQuantity: Int = 0,
    val Description: String?,
    val Category: String,
    @get:Size(min = 4, max = 60) val CustomerID: String,
)
