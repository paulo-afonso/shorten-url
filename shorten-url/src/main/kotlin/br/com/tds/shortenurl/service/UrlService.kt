package br.com.tds.shortenurl.service

import br.com.tds.shortenurl.controller.dto.UrlFormDto
import br.com.tds.shortenurl.controller.dto.UrlResponseDto
import br.com.tds.shortenurl.exception.LinkExpiredException
import br.com.tds.shortenurl.exception.LinkNotFoundException
import br.com.tds.shortenurl.extensions.mapToResponseDto
import br.com.tds.shortenurl.model.UrlModel
import br.com.tds.shortenurl.repository.UrlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.net.URI
import java.time.LocalDateTime
import javax.servlet.http.HttpServletResponse


@Service
class UrlService(
    private var urlRepository: UrlRepository,
    @Autowired
    private var statsService: StatsService
) {

    fun listing(): ResponseEntity<List<UrlModel>> {
        return ResponseEntity.ok(urlRepository.findAll())
    }


    fun getLinkAndCountAccess(shortUrl: String): ResponseEntity<String> {
        val url = urlRepository.findByShortUrl(shortUrl) ?: throw LinkNotFoundException()

        if (url.expirationDate!!.isBefore(LocalDateTime.now())) {
            urlRepository.delete(url)
            throw LinkExpiredException()
        }

        statsService.countAccess(shortUrl)
        val originalUrl = url.originalUrl
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build()
    }


    private fun genShortString(length: Int = 5): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun register(urlFormDto: UrlFormDto): ResponseEntity<UrlResponseDto> {
        val newEndpoint = genShortString()
        val urlModel = UrlModel(
            id = null,
            shortUrl = null,
            originalUrl = urlFormDto.originalUrl,
            creationDate = LocalDateTime.now(),
            expirationTime = null,
            expirationDate = null,
        )
//    Checking if short endpoint already exists on repository.
        if (urlRepository.findByShortUrl(newEndpoint) == null) {
            urlModel.shortUrl = newEndpoint

            // If user doesn't set expiration time, set to 1 minute. Else, set to user's choice.
            if (urlFormDto.expirationTime == null) {
                urlModel.expirationTime = 1
                urlModel.expirationDate = urlModel.creationDate.plusMinutes(urlModel.expirationTime!!)
            } else {
                urlModel.expirationDate = urlModel.creationDate.plusMinutes(urlFormDto.expirationTime)
            }
            return ResponseEntity.ok(urlRepository.save(urlModel).mapToResponseDto())
        }
        return register(urlFormDto)
    }
}