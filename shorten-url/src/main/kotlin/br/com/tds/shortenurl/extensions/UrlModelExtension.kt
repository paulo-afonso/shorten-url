package br.com.tds.shortenurl.extensions

import br.com.tds.shortenurl.controller.dto.UrlResponseDto
import br.com.tds.shortenurl.model.UrlModel


fun UrlModel.mapToResponseDto() = UrlResponseDto(
    originalUrl = originalUrl,
    shortenUrl = shortUrl,
    expirationDate = expirationDate
)