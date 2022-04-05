package br.com.tds.shortenurl.repository

import br.com.tds.shortenurl.model.StatsModel
import br.com.tds.shortenurl.model.UrlModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface StatsRepository: MongoRepository<StatsModel, String> {
    fun findStatsByShortUrl(shortUrl: String): StatsModel?
}