package nrise.urlshortener.exception

import nrise.urlshortener.common.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    fun badRequestException(exception: BadRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity<ErrorResponse>(
            ErrorResponse(
                message = exception.message,
                code = "BAD_REQUEST"
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(exception: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                message = exception.message,
                code = "NOT_FOUND"
            ),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(BaseException::class)
    fun exception(exception: BaseException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                message = exception.message,
                code = "INTERNAL_SERVER_ERROR"
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
