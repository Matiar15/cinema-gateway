package pl.szudor

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.context.request.WebRequest
import pl.szudor.exception.ErrorDto
import pl.szudor.exception.NotExistsException
import java.time.LocalDateTime

@RestControllerAdvice
class ControllerExceptionHandler: DefaultResponseErrorHandler() {
    @ExceptionHandler(value = [NotExistsException::class])
    fun notExistsException(ex: NotExistsException, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ErrorDto(LocalDateTime.now(), 404, ex.message.toString()), HttpStatus.NOT_FOUND)
    }
}