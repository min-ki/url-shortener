package nrise.urlshortener.exception

class ShortLinkNotFoundException(
    override val message: String,
    override val code: String = "SHORT_LINK_NOT_FOUND"
) : NotFoundException(message, code)
