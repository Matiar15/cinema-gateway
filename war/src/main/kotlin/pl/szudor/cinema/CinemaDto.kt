package pl.szudor.cinema

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime

data class CinemaDto(
    @field:Schema(description = "ID")
    val id: Int,
    @field:Schema(description = "Name")
    val name: String,
    @field:Schema(description = "Address")
    val address: String,
    @field:Schema(description = "Email")
    val email: String,
    @field:Schema(description = "Phone number")
    val phoneNumber: String,
    @field:Schema(description = "Postal code")
    val postalCode: String,
    @field:Schema(description = "Director name")
    val director: String,
    @field:Schema(description = "NIP")
    val nipCode: String,
    @field:Schema(description = "Build date")
    val buildDate: LocalDate,
    @field:Schema(description = "State")
    var active: Active,
    @field:Schema(description = "Created at")
    val createdAt: LocalDateTime
)