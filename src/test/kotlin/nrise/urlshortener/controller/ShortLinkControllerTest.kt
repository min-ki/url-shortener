package nrise.urlshortener.controller

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import nrise.urlshortener.controller.request.CreateShortLinkRequest
import nrise.urlshortener.controller.response.ShortLinkResponse
import nrise.urlshortener.service.ShortLinkService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 실제 서버를 띄우고 테스트
@AutoConfigureMockMvc // MockMvc를 사용하기 위해 필요
@Transactional // 각 테스트 메소드 실행 후 롤백
class ShortLinkControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper // JSON <-> Object 매핑을 위한 ObjectMapper

    @Autowired
    private lateinit var shortLinkService: ShortLinkService

    @Test
    @DisplayName("origin_url을 입력받아 shortLink를 생성할 수 있어야 함")
    fun createShortLink() {
        // given
        val createShortLinkRequest = CreateShortLinkRequest(
            url = "https://quat.life"
        )

        // when
        val response = mockMvc.perform(
            post("/short-links")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createShortLinkRequest))
        ).andReturn().response

        // then
        assertEquals(200, response.status)

        val shortLinkResponse = objectMapper.readValue(response.contentAsString, ShortLinkResponse::class.java)
        assertEquals("https://quat.life", shortLinkResponse.url)
        assertEquals(6, shortLinkResponse.shortUrlId.length)
    }

    @Test
    @DisplayName("shortUrlId를 통해 shortLink에 대한 origin_url을 조회할 수 있어야 함")
    fun getShortLink() {
        // given
        val shortLink = shortLinkService.createShortLink("https://quat.life")

        // when
        val response = mockMvc.perform(
            get("/short-links/${shortLink.shortUrlId}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().response

        // then
        assertEquals(200, response.status)

        val shortLinkResponse = objectMapper.readValue(response.contentAsString, ShortLinkResponse::class.java)
        assertEquals("https://quat.life", shortLinkResponse.url)
        assertEquals(shortLink.shortUrlId, shortLinkResponse.shortUrlId)
    }

    @Test
    @DisplayName("shortUrlId를 통해 redirect 요청을 보내면 originUrl로 redirect 되어야 함")
    fun redirect() {
        // given
        val shortLink = shortLinkService.createShortLink("https://quat.life")

        // when
        val response = mockMvc.perform(
            get("/redirect/${shortLink.shortUrlId}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().response

        // then
        assertEquals(302, response.status)
        assertEquals("https://quat.life", response.getHeader("Location"))
    }
}
