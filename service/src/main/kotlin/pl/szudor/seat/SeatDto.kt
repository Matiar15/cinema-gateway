package pl.szudor.seat

import javax.validation.constraints.NotNull

data class SeatDto(
    val id: Int?,
    @NotNull
    val seatNumber: Int?,
)