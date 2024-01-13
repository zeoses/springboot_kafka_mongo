package com.earth.entity

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.annotation.Id

@Document(collection="Customer")
data class CusromerEntity(
        @Id
        @Field
        val CustomerID: String,
        val FirstName: String,
        val LastName: Boolean,
        val Email: String?,
        val Address: String?,
        val Phone: String?
)