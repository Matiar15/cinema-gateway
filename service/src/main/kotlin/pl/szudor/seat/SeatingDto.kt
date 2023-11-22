package pl.szudor.seat

import javax.validation.constraints.NotNull

data class SeatingDto(
    val id: Int?,
    @NotNull
    val seatNumber: Int?,
)