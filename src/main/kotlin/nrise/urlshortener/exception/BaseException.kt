package nrise.urlshortener.exception

abstract class BaseException(override val message: String, open val code: String) : RuntimeException(message)
