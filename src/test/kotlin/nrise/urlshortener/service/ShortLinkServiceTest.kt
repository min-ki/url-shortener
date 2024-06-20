package nrise.urlshortener.service

import io.mockk.every
import io.mockk.mockk
import nrise.urlshortener.entity.ShortLinkEntity
import nrise.urlshortener.repository.ShortLinkRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import java.util.Optional

class ShortLinkServiceTest {

    @Test
    @DisplayName("originUrl을 입력받아 shortLink를 생성합니다.")
    fun createShortLink() {
        // Given
        val shortLinkRepository = mockk<ShortLinkRepository>()
        val shortLinkService = ShortLinkService(shortLinkRepository)
        val originUrl = "https://quat.life"

        every { shortLinkRepository.findByOriginUrl(originUrl) } returns Optional.empty()
        every { shortLinkRepository.save(any()) } answers {
            ShortLinkEntity(
                id = 1L,
                shortUrlId = "shortUrlId",
                originUrl = originUrl,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }

        // When
        val shortLink = shortLinkService.createShortLink(originUrl)

        // Then
        // case1. shortUrl은 3글자 이상의 alpha-numeric 한 unique 문자열
        assert(shortLink.shortUrlId.length >= 3)
        assert(shortLink.shortUrlId.matches(Regex("[a-zA-Z0-9]+")))

        // case2. originUrl은 입력한 originUrl과 같아야 함
        assert(shortLink.originUrl == originUrl)
    }

    @Test
    fun getShortLink() {
        // Given
        val shortLinkRepository = mockk<ShortLinkRepository>()
        val shortLinkService = ShortLinkService(shortLinkRepository)
        val shortUrlId = "588d68"

        every { shortLinkRepository.findByShortUrlId(shortUrlId) } answers {
            Optional.of(ShortLinkEntity(
                id = 1L,
                shortUrlId = shortUrlId,
                originUrl = "https://quat.life",
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            ))
        }

        // When
        val shortLink = shortLinkService.getShortLink(shortUrlId)

        // Then
        assert(shortLink.shortUrlId == shortUrlId)
        assert(shortLink.shortUrlId.length >= 3)
        assert(shortLink.shortUrlId.matches(Regex("[a-zA-Z0-9]+")))
    }
}
