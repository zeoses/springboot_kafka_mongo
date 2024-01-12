package com.mars.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration


@OpenAPIDefinition(
    info = Info(
        title = "Mars",
        description = "Mars",
        contact = Contact(name = "Mars", email = "Mars@Mars.ir", url = "Mars.ir")
    )
)
@Configuration
class SwaggerOpenAPIConfiguration