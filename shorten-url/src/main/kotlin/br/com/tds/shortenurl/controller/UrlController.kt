package br.com.tds.shortenurl.controller

import br.com.tds.shortenurl.controller.dto.UrlFormDto
import br.com.tds.shortenurl.controller.dto.UrlResponseDto
import br.com.tds.shortenurl.model.UrlModel
import br.com.tds.shortenurl.service.StatsService
import br.com.tds.shortenurl.service.UrlService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping
class UrlController(
    private val urlService: UrlService,
    private val statsService: StatsService
    ) {

    @GetMapping("/list")
    fun listing(): ResponseEntity<List<UrlModel>> {
        return urlService.listing()
    }

    @GetMapping("/{shortLink}")
    fun getOriginalLink(@PathVariable shortLink: String, response: HttpServletResponse) {
        val originalUrl = urlService.getOriginalLink(shortLink)
        statsService.countAccess(shortLink)
        if (originalUrl == "Bad Request") {
            response.sendError(404, "Your link expired, please generate another link")
        }
        response.setHeader("Location", originalUrl)
        response.status = 302
    }

    @PostMapping("/generate")
    fun register(@RequestBody urlFormDto: UrlFormDto): ResponseEntity<UrlResponseDto> {
        return urlService.register(urlFormDto)
    }

}