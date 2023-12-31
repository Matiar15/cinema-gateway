package pl.szudor.seat.reserved

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class ReservedSeatPayload(
    @field:Schema(description = "Seat ID")
    @field:NotNull
    @field:Positive
    val id: Int?
) {
}