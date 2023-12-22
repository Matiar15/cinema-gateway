package pl.szudor.room

import io.swagger.v3.oas.annotations.media.Schema

data class RoomDto(
    @field:Schema(description = "ID")
    val id: Int,
    @field:Schema(description = "Number")
    val number: Int
)