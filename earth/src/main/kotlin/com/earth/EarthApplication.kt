package com.earth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EarthApplication

fun main(args: Array<String>) {
	runApplication<EarthApplication>(*args)
}
