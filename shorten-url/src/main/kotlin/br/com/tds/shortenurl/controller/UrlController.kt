package br.com.tds.shortenurl.controller

import br.com.tds.shortenurl.controller.dto.UrlFormDto
import br.com.tds.shortenurl.controller.dto.UrlResponseDto
import br.com.tds.shortenurl.model.UrlModel
import br.com.tds.shortenurl.service.StatsService
import br.com.tds.shortenurl.service.UrlService
import org.springframework.http.HttpStatus
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
    @ResponseStatus(HttpStatus.FOUND)
    fun getOriginalLink(@PathVariable shortLink: String): ResponseEntity<String> {
        return urlService.getLinkAndCountAccess(shortLink)
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody urlFormDto: UrlFormDto): ResponseEntity<UrlResponseDto> {
        return urlService.register(urlFormDto)
    }
}
