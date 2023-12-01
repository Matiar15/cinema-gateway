package pl.szudor.room

import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class RoomPayload(
    @field:NotNull
    @field:Positive
    val number: Int?
)