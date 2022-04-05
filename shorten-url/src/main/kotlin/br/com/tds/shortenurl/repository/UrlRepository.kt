package br.com.tds.shortenurl.repository

import br.com.tds.shortenurl.model.UrlModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository: MongoRepository<UrlModel, String> {
    fun findByShortUrl(shortUrl: String): UrlModel?
}