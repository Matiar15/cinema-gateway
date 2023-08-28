package pl.szudor.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@RestControllerAdvice
class DefaultExceptionHandler: DefaultResponseErrorHandler() {
    @ExceptionHandler(value = [(NotExistsException::class)])
    fun handleNotExistsException(ex: NotExistsException, request: WebRequest): ResponseEntity<ErrorDto> {
        val status = HttpStatus.NOT_FOUND
        val errorDto = ErrorDto(LocalDateTime.now(), status.value(), ex.message.toString())
        return ResponseEntity(errorDto, status)
    }
}