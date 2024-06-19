package nrise.urlshortener.exception

abstract class BadRequestException(
    message: String,
    override val code: String = "BAD_REQUEST",
) : BaseException(message, code)
