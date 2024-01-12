package com.mars.dto


data class CustomerDTO (
    val CustomerID: String,
    val FirstName: String,
    val LastName: String,
    val Email: String?,
    val Address: String?,
    val Phone: String?,
)
