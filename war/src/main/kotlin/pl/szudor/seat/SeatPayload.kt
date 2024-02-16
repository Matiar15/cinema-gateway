package pl.szudor.seat

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class SeatPayload(
    @field:Schema(description = "Number")
    @field:NotNull
    @field:Positive
    val number: Int?
)