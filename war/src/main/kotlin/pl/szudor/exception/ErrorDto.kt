package pl.szudor.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime


data class ErrorDto(
    val timestamp: LocalDateTime,
    val message: String
) {
    constructor(message: String) : this(LocalDateTime.now(), message)

    fun toResponseEntity(status: HttpStatus): ResponseEntity<ErrorDto> = ResponseEntity(this, status)
}