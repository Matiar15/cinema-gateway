package pl.szudor.seat

import io.swagger.v3.oas.annotations.media.Schema

data class SeatDto(
    @field:Schema(description = "ID")
    val id: Int,
    @field:Schema(description = "Number")
    val number: Int,
    @field:Schema(description = "Occupied indicator")
    val occupied: Occupied
)