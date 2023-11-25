package pl.szudor.cinema

import java.time.LocalDate
import java.time.LocalDateTime

data class CinemaDto(
    val id: Int,
    val name: String,
    val address: String,
    val email: String,
    val phoneNumber: String,
    val postalCode: String,
    val director: String,
    val nipCode: String,
    val buildDate: LocalDate,
    var state: State,
    val createdAt: LocalDateTime
)