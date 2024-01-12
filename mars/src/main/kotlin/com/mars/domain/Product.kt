package com.mars.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.mars.dto.ProductDTO
import java.time.Instant
import java.util.*


@JsonIgnoreProperties(ignoreUnknown = true)
class Product(
    @JsonProperty(value = "ProductID") var ProductID: String = "",
    @JsonProperty(value = "Name") var Name: String = "",
    @JsonProperty(value = "Price") var Price: Double = 0.0,
    @JsonProperty(value = "StockQuantity") var StockQuantity: Int = 0,
    @JsonProperty(value = "Description") var Description: String? = "",
    @JsonProperty(value = "Category") var Category: String = "",
    @JsonProperty(value = "CustomerID") var CustomerID: String = "",
) {

    companion object {
        fun of(productDTO: ProductDTO): Product {
            return Product(
                ProductID = productDTO.ProductID,
                Name = productDTO.Name,
                Price = productDTO.Price,
                StockQuantity = productDTO.StockQuantity,
                Description = productDTO.Description,
                Category = productDTO.Category,
                CustomerID = productDTO.CustomerID
            )
        }
    }


    override fun toString(): String {
        return "Product(ProductID='$ProductID', Name='$Name', Price='$Price', StockQuantity='$StockQuantity', Description='$Description', Category='$Category', CustomerID='$CustomerID')"
    }
}
