package pl.szudor.cinema

import io.swagger.v3.oas.annotations.media.Schema
import pl.szudor.utils.PhoneNumber
import java.time.LocalDate
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class CinemaPayload(
    @field:Schema(description = "Name")
    @field:NotNull
    val name: String?,

    @field:Schema(description = "Address")
    @field:NotNull
    val address: String?,

    @field:Schema(description = "Email")
    @field:Email
    @field:NotNull
    val email: String?,

    @field:Schema(description = "Phone number")
    @field:PhoneNumber
    val phoneNumber: String?,

    @field:Schema(description = "Postal code")
    @field:Pattern(regexp = "([0-9]{2,3})-([0-9]{2,3})")
    @field:NotNull
    val postalCode: String?,

    @field:Schema(description = "Director name")
    @field:NotNull
    val director: String?,

    @field:Schema(description = "Nip code")
    @field:Pattern(regexp = "[0-9]{10}")
    @field:NotNull
    val nipCode: String?,

    @field:Schema(description = "Build date")
    @field:NotNull
    val buildDate: LocalDate?
)