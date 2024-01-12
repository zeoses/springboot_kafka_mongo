package com.mars.dto
import jakarta.validation.constraints.Size

data class CustomerDTO (
    @get:Size(min = 4, max = 60) val CustomerID: String,
    @get:Size(min = 4, max = 200) val FirstName: String,
    @get:Size(min = 4, max = 200) val LastName: String,
    val Email: String?,
    val Address: String?,
    val Phone: String?,
)
