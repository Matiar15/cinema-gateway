package pl.szudor.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.context.request.WebRequest
import pl.szudor.exception.generic.BadRequestException
import pl.szudor.exception.generic.NotExistsException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class DefaultExceptionHandler: DefaultResponseErrorHandler() {
    @ExceptionHandler(value = [NotExistsException::class])
    fun handleNotExistsException(ex: NotExistsException, request: WebRequest): ResponseEntity<ErrorDto> =
        ErrorDto(ex.localizedMessage).toResponseEntity(HttpStatus.NOT_FOUND)

    @ExceptionHandler(value = [BadRequestException::class])
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorDto> =
        ErrorDto(ex.localizedMessage).toResponseEntity(HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleConstraintViolation(ex: ConstraintViolationException, request: WebRequest): ResponseEntity<ErrorDto> =
        ErrorDto(ex.localizedMessage).toResponseEntity(HttpStatus.BAD_REQUEST)
}