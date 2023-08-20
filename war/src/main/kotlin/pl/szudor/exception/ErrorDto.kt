package pl.szudor.exception

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime


data class ErrorDto(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        val timestamp: LocalDateTime,
        val status: Int,
        val error: String)

