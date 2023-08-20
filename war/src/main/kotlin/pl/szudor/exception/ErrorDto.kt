package pl.szudor.exception

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime


class ErrorDto(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        private val timestamp: LocalDateTime,
        private val status: Int,
        private val error: String)

