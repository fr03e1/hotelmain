package com.hotelmain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HotelServiceApplication

fun main(args: Array<String>) {
	runApplication<HotelServiceApplication>(*args)
}
