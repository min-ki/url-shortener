package nrise.urlshortener.repository

import nrise.urlshortener.entity.ShortLinkEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ShortLinkRepository: JpaRepository<ShortLinkEntity, Long> {
    fun findByShortUrlId(shortUrlId: String): Optional<ShortLinkEntity>
    fun findByOriginUrl(originUrl: String): Optional<ShortLinkEntity>
}
