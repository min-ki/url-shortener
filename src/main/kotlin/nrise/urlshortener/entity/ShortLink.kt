package nrise.urlshortener.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "short_link")
class ShortLinkEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "short_url_id", unique= true)
    val shortUrlId: String,

    @Column(name = "origin_url")
    val originUrl: String,

    @Column(name = "created_at")
    val createdAt: ZonedDateTime,

    @Column(name = "updated_at")
    val updatedAt: ZonedDateTime
)
