package br.com.tds.shortenurl

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShortenUrlApplication

fun main(args: Array<String>) {
	runApplication<ShortenUrlApplication>(*args)
}
