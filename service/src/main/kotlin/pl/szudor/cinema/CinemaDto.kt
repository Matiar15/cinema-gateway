package pl.szudor.cinema

import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class CinemaDto (
        val id: Int?,
        @field:NotNull
        val name: String?,

        @field:NotNull
        val address: String?,

        @field:Pattern(regexp = "[a-zA-z-0-9]{4,25}@([a-z]{4,18})\\.([a-z]{2,3})")
        @field:NotNull
        val email: String?,


        @field:Pattern(regexp = "\\+([0-9]{2,3}) ([0-9]{3})-([0-9]{3})-([0-9]{3})")
        @field:NotNull
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
        val buildDate: LocalDate?,

        @field:NotNull
        var currentState: CinemaState?,

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
                if (currentState != other.currentState) return false
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
                result = 31 * result + (currentState?.hashCode() ?: 0)
                result = 31 * result + (createdAt?.hashCode() ?: 0)
                return result
        }
}

