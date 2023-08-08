package pl.szudor.cinema

import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

data class CinemaDto (
        val id: Int?,

        @NotNull
        val name: String?,
        @NotNull
        val street: String?,
        @NotNull
        val phoneNumber: String?,
        @NotNull
        val postalCode: String?,
        @NotNull
        val director: String?,
/*
        @NotNull
        val nipCode: String?,
        @NotNull
        val buildDate: LocalDate?,
        @NotNull
        val builtIn: LocalDate?,
        @NotNull
        val currentState: CinemaState? = CinemaState.OFF,
        @NotNull
        val workingHours: CinemaSchedule?,
*/
        val createdAt: LocalDateTime?
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as CinemaDto

                if (id != other.id) return false
                if (name != other.name) return false
                if (street != other.street) return false
                if (phoneNumber != other.phoneNumber) return false
                if (postalCode != other.postalCode) return false
                if (director != other.director) return false

                return true
        }

        override fun hashCode(): Int {
                var result = id ?: 0
                result = 31 * result + (name?.hashCode() ?: 0)
                result = 31 * result + (street?.hashCode() ?: 0)
                result = 31 * result + (phoneNumber?.hashCode() ?: 0)
                result = 31 * result + (postalCode?.hashCode() ?: 0)
                result = 31 * result + (director?.hashCode() ?: 0)
                return result
        }
}

