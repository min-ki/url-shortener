package nrise.urlshortener.controller

import nrise.urlshortener.controller.request.CreateShortLinkRequest
import nrise.urlshortener.controller.response.ShortLinkResponse
import nrise.urlshortener.service.ShortLinkService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ShortLinkController(
    val shortLinkService: ShortLinkService
) {
    @PostMapping("/short-links")
    fun createShortLink(@RequestBody body: CreateShortLinkRequest): ShortLinkResponse {
        val shortLink = shortLinkService.createShortLink(body.url)

        return ShortLinkResponse(
            shortUrlId = shortLink.shortUrlId,
            url = shortLink.originUrl,
            createdAt = shortLink.createdAt,
        )
    }

    @GetMapping("/short-links/{shortUrlId}")
    fun getShortLink(@PathVariable shortUrlId: String): ShortLinkResponse {
        val shortLink = shortLinkService.getShortLink(shortUrlId)
        return ShortLinkResponse(
            shortUrlId = shortLink.shortUrlId,
            url = shortLink.originUrl,
            createdAt = shortLink.createdAt,
        )
    }

    @GetMapping("/redirect/{shortUrlId}")
    fun redirect(@PathVariable shortUrlId: String): ResponseEntity<Void> {
        val shortLink = shortLinkService.getShortLink(shortUrlId)

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(java.net.URI(shortLink.originUrl))
            .build()
    }
}
