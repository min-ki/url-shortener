package nrise.urlshortener.exception

abstract class NotFoundException(
    message: String,
    override val code: String = "NOT_FOUND"
) : BaseException(message, code)
