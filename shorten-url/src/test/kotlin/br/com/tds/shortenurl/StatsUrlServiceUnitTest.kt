package br.com.tds.shortenurl


import br.com.tds.shortenurl.model.StatsModel
import br.com.tds.shortenurl.model.UrlModel
import br.com.tds.shortenurl.repository.StatsRepository
import br.com.tds.shortenurl.repository.UrlRepository
import br.com.tds.shortenurl.service.StatsService
import io.mockk.MockKAnnotations
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class StatsUrlServiceUnitTest {
    @InjectMockKs
    lateinit var statsService: StatsService
    @MockK
    lateinit var statsRepository: StatsRepository
    @MockK
    lateinit var urlRepository: UrlRepository


    @BeforeEach
    fun setup(){
        MockKAnnotations.init(this)
    }

    @Test
    fun `When I access a short link 'x' times Then it should increase the stats counter by 1 for each access`() {
        every { statsRepository.save(any()) } answers { firstArg() }
        every { urlRepository.save(any()) } answers { firstArg() }


        val urlModel = UrlModel(
            id = null,
            shortUrl = "awB21",
            originalUrl = "https://google.com",
            creationDate = LocalDateTime.now(),
            expirationTime = 10,
            expirationDate = (LocalDateTime.now().plusMinutes(10)),
        )

        every { urlRepository.findByShortUrl(any()) } answers { urlModel }


        val statsModel = StatsModel(
            id = null,
            shortUrl = "awB21",
            originalUrl = "https://google.com",
            accessCounter = 0
        )

        every { statsRepository.findStatsByShortUrl(any()) } returns statsModel

        urlRepository.save(urlModel)

        repeat(10) {statsService.countAccess(statsModel.shortUrl!!)}

        assertThat(statsRepository.findStatsByShortUrl(statsModel.shortUrl!!)).isNotNull
        assertThat(statsRepository.findStatsByShortUrl(statsModel.shortUrl!!)!!.accessCounter).isEqualTo(10)

    }
}