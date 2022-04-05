package br.com.tds.shortenurl.controller.dto

import java.time.LocalDateTime

data class UrlResponseDto (
    val originalUrl: String,
    val shortenUrl: String?,
    val expirationDate: LocalDateTime?
)