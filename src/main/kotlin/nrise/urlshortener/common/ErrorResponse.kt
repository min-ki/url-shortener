package nrise.urlshortener.common

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val code: String,
    val title: String? = null,
    @Required
    val extra: Map<String, List<String>>? = null,
)
