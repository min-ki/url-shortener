package nrise.urlshortener.controller.response

import java.time.ZonedDateTime

data class ShortLinkResponse(
    val shortUrlId: String,
    val url: String,
    val createdAt: ZonedDateTime
)
