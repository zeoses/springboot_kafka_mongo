package com.mars.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.mars.dto.CustomerDTO
import java.time.Instant
import java.util.*


@JsonIgnoreProperties(ignoreUnknown = true)
class Customer(
    @JsonProperty(value = "CustomerID") var CustomerID: String = "",
    @JsonProperty(value = "FirstName") var FirstName: String = "",
    @JsonProperty(value = "LastName") var LastName: String = "",
    @JsonProperty(value = "Email") var Email: String? = "",
    @JsonProperty(value = "Address") var Address: String? = "",
    @JsonProperty(value = "Phone") var Phone: String? = "",
) {

    companion object {
        fun of(customerDTO: CustomerDTO): Customer {
            return Customer(
                CustomerID = customerDTO.CustomerID,
                FirstName = customerDTO.FirstName,
                LastName = customerDTO.LastName,
                Email = customerDTO.Email,
                Address = customerDTO.Address,
                Phone = customerDTO.Phone,
            )
        }
    }


    override fun toString(): String {
        return "Product(CustomerID='$CustomerID', FirstName='$FirstName', LastName='$LastName', Email='$Email', Address='$Address', Phone='$Phone')"
    }
}
