package br.com.tds.shortenurl.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id

@Document
data class StatsModel (
    @Id
    val id: String?,
    var shortUrl: String?,
    val originalUrl: String?,
    var accessCounter: Long
)