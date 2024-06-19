package nrise.urlshortener.dto

import nrise.urlshortener.entity.ShortLinkEntity
import java.time.ZonedDateTime

data class ShortLinkDto(
    val shortUrlId: String,
    val originUrl: String,
    val createdAt: ZonedDateTime,
) {
    companion object {
        fun from(obj: ShortLinkEntity): ShortLinkDto {
            return ShortLinkDto(
                shortUrlId = obj.shortUrlId,
                originUrl = obj.originUrl,
                createdAt = obj.createdAt
            )
        }
    }
}
