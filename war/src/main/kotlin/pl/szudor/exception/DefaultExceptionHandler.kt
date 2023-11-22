package pl.szudor.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.context.request.WebRequest
import pl.szudor.exception.generic.ErrorDto
import pl.szudor.exception.generic.NotExistsException

@RestControllerAdvice
class DefaultExceptionHandler: DefaultResponseErrorHandler() {
    @ExceptionHandler(value = [NotExistsException::class])
    fun handleNotExistsException(ex: NotExistsException, request: WebRequest): ResponseEntity<ErrorDto> =
        ErrorDto(ex.message.toString()).toResponseEntity(HttpStatus.NOT_FOUND)

}

fun ErrorDto.toResponseEntity(status: HttpStatus): ResponseEntity<ErrorDto> = ResponseEntity(this, status)