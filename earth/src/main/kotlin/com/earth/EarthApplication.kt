package com.earth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class EarthApplication

fun main(args: Array<String>) {
	runApplication<EarthApplication>(*args)
}
