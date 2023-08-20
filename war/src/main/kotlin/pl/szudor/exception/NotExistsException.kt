package pl.szudor.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

open class NotExistsException : RuntimeException {
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
    constructor(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                timestamp: LocalDateTime = LocalDateTime.now(),
                ex: Throwable,
                message: String)
}
