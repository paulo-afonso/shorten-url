package br.com.tds.shortenurl.exception

import br.com.tds.shortenurl.common.Constants
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND, reason = Constants.LINK_EXPIRED)
class LinkExpiredException : RuntimeException()