package br.com.tds.shortenurl

import br.com.tds.shortenurl.repository.UrlRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
//@DataMongoTest
class ShortenUrlIntegrationTests(

) {
	@Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
	lateinit var urlRepository: UrlRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun contextLoads() {
    }


//    @Test
//    fun `Testing if successfully post request`() {
//        val baseUrl = "/generate"
//		val urlFormDto = UrlFormDto(originalUrl = "https://google.com", expirationTime = null)
//
//        mockMvc.post(baseUrl) {
//            contentType = MediaType.APPLICATION_JSON
//            content = objectMapper.writeValueAsString(urlFormDto)
//        }
//            .andExpect {
//                status { isCreated() }
//                content {
//                    contentType(MediaType.APPLICATION_JSON)
//                }
//            }
//    }

}
