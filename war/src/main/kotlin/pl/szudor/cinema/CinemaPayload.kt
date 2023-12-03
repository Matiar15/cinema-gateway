package pl.szudor.cinema

import pl.szudor.utils.PhoneNumber
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class CinemaPayload(
    @field:NotNull
    val name: String?,

    @field:NotNull
    val address: String?,

    @field:Email
    @field:NotNull
    val email: String?,

    @field:PhoneNumber
    val phoneNumber: String?,

    @field:Pattern(regexp = "([0-9]{2,3})-([0-9]{2,3})")
    @field:NotNull
    val postalCode: String?,

    @field:NotNull
    val director: String?,

    @field:Pattern(regexp = "[0-9]{10}")
    @field:NotNull
    val nipCode: String?,

    @field:NotNull
    val buildDate: LocalDate?
)