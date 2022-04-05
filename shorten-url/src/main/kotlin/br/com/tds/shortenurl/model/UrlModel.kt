package br.com.tds.shortenurl.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class UrlModel(
    @Id
    val id: String?,
    var shortUrl: String?,
    var originalUrl: String,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    var expirationTime: Long?,
    var expirationDate: LocalDateTime?
)