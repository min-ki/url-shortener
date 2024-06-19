package nrise.urlshortener.service

import nrise.urlshortener.dto.ShortLinkDto
import nrise.urlshortener.entity.ShortLinkEntity
import nrise.urlshortener.exception.ShortLinkNotFoundException
import nrise.urlshortener.util.encodeToID
import nrise.urlshortener.repository.ShortLinkRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class ShortLinkService(
    val shortLinkRepository: ShortLinkRepository
) {
    fun createShortLink(originUrl: String): ShortLinkDto {

        val existShortLink = shortLinkRepository.findByOriginUrl(originUrl)
        if (existShortLink.isPresent) {
            return ShortLinkDto.from(existShortLink.get())
        }

        // NOTE: 동일한 originalUrl에 대해서는 동일한 shortUrl을 반환
        val shortLinkEntity = ShortLinkEntity(
            shortUrlId = originUrl.encodeToID(),
            originUrl = originUrl,
            createdAt = ZonedDateTime.now(ZoneId.of("UTC")),
            updatedAt = ZonedDateTime.now(ZoneId.of("UTC"))
        )

        try {
            val newShortLinkEntity = shortLinkRepository.save(
                shortLinkEntity
            )
            return ShortLinkDto.from(newShortLinkEntity)
        } catch (e: DataIntegrityViolationException) {
            return shortLinkRepository.findByOriginUrl(originUrl).orElse(null)?.let {
                ShortLinkDto.from(it)
            } ?: throw ShortLinkNotFoundException("shortLink를 찾을 수 없습니다.")
        }

    }

    fun getShortLink(shortUrlId: String): ShortLinkDto {
        val shortLinkEntity = shortLinkRepository.findByShortUrlId(shortUrlId).orElseThrow {
            throw ShortLinkNotFoundException(
                "shortLink를 찾을 수 없습니다."
            )
        }

        return ShortLinkDto.from(shortLinkEntity)
    }
}
