package pl.szudor.cinema

import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class CinemaDto(
    val id: Int?,

    @field:NotNull(groups = [Validation.Create::class])
    val name: String?,

    @field:NotNull(groups = [Validation.Create::class])
    val address: String?,

    @field:Email
    @field:NotNull(groups = [Validation.Create::class, Validation.Update::class])
    val email: String?,

    @field:Pattern(regexp = "\\+([0-9]{2,3})-([0-9]{3})-([0-9]{3})-([0-9]{3})\$")
    @field:NotNull(groups = [Validation.Create::class])
    val phoneNumber: String?,
    @field:Pattern(groups = [Validation.Create::class, Validation.Update::class], regexp = "([0-9]{2,3})-([0-9]{2,3})")
    @field:NotNull(groups = [Validation.Create::class])
    val postalCode: String?,

    @field:NotNull(groups = [Validation.Create::class])
    val director: String?,

    @field:Pattern(groups = [Validation.Create::class, Validation.Update::class], regexp = "[0-9]{10}")
    @field:NotNull(groups = [Validation.Create::class])
    val nipCode: String?,

    @field:NotNull(groups = [Validation.Create::class])
    val buildDate: LocalDate?,

    @field:NotNull(groups = [Validation.Create::class])
    var state: State?,

    val createdAt: LocalDateTime?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CinemaDto

        if (name != other.name) return false
        if (address != other.address) return false
        if (email != other.email) return false
        if (phoneNumber != other.phoneNumber) return false
        if (postalCode != other.postalCode) return false
        if (director != other.director) return false
        if (nipCode != other.nipCode) return false
        if (buildDate != other.buildDate) return false
        if (state != other.state) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        result = 31 * result + (postalCode?.hashCode() ?: 0)
        result = 31 * result + (director?.hashCode() ?: 0)
        result = 31 * result + (nipCode?.hashCode() ?: 0)
        result = 31 * result + (buildDate?.hashCode() ?: 0)
        result = 31 * result + (state?.hashCode() ?: 0)
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        return result
    }
}

interface Validation {
    interface Create
    interface Update
}