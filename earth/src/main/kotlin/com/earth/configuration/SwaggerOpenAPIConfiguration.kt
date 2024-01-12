package com.earth.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration


@OpenAPIDefinition(
    info = Info(
        title = "earth",
        description = "earth",
        contact = Contact(name = "earth", email = "earth@Mars.ir", url = "earth.ir")
    )
)
@Configuration
class SwaggerOpenAPIConfiguration