package br.com.tds.shortenurl

import br.com.tds.shortenurl.controller.dto.UrlFormDto
import br.com.tds.shortenurl.model.UrlModel
import br.com.tds.shortenurl.repository.UrlRepository
import br.com.tds.shortenurl.service.UrlService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import java.time.LocalDateTime


class ShortenUrlServiceUnitTest {
    @InjectMockKs
    lateinit var urlService: UrlService
    @MockK
    lateinit var urlRepository: UrlRepository

    @BeforeEach
    fun setup(){
        MockKAnnotations.init(this)
    }

    @Test
    fun `When I try to short an url Then it should return a shortened endpoint that redirects to the original url`() {
        every { urlRepository.findByShortUrl(any()) } returns null
        every { urlRepository.save(any()) } answers {firstArg()}

        val urlFormDto = UrlFormDto("https://google.com", expirationTime = null)

        val responseDto = urlService.register(urlFormDto)

        assertThat(responseDto).isNotNull
        assertThat(responseDto.body?.shortenUrl?.length).isEqualTo(5)
    }


    @Test
    fun `When I try to access original url Then it should get shortened endpoint and redirect to correspondent url`() {
        every { urlRepository.save(any()) } answers { firstArg() }

        val urlModel = UrlModel(
            id = null,
            shortUrl = "awB21",
            originalUrl = "https://google.com",
            creationDate = LocalDateTime.now(),
            expirationTime = 10,
            expirationDate = (LocalDateTime.now().plusMinutes(10)),
        )

        every { urlRepository.findByShortUrl(any()) } returns urlModel
        urlRepository.save(urlModel)

        val originalUrl = urlService.getOriginalLink(urlModel.shortUrl!!)

        assertThat(originalUrl).isNotNull
        assertThat(originalUrl).isEqualTo("https://google.com")
        }


    @Test
    fun `When I try to get a list of my registered objects Then it should return all the models in the repository`() {
        val urlModel = UrlModel(
            id = null,
            shortUrl = "awB21",
            originalUrl = "https://google.com",
            creationDate = LocalDateTime.now(),
            expirationTime = 10,
            expirationDate = (LocalDateTime.now().plusMinutes(10)),
        )

        every { urlRepository.findAll() } returns listOf(urlModel)
        val list = urlService.listing()

        assertThat(list).isNotNull
        assertThat(list.body?.size).isEqualTo(1)
    }

}