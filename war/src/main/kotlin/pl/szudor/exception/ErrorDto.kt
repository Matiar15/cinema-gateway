package pl.szudor.exception

import java.time.LocalDateTime


data class ErrorDto(
    val timestamp: LocalDateTime,
    val message: String
) {
    constructor(message: String) : this(LocalDateTime.now(), message)
}