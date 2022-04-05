package br.com.tds.shortenurl.controller.dto

import java.time.LocalDateTime

data class UrlFormDto (
    val originalUrl: String,
    val expirationTime: Long?
)