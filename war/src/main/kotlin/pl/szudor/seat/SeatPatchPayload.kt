package pl.szudor.seat

import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class SeatPostPayload(
    @field:NotNull
    @field:Positive
    val number: Int?
)
data class SeatPatchPayload(
    @field:NotNull
    val occupied: Occupied?
)