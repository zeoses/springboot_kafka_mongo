package com.mars

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MarsApplication

fun main(args: Array<String>) {
	runApplication<MarsApplication>(*args)
}
