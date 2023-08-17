package pl.szudor.cinema

import com.fasterxml.jackson.annotation.JsonBackReference
import org.jetbrains.annotations.NotNull
import pl.szudor.repertoire.RepertoireDto
import java.time.LocalDate
import java.time.LocalDateTime

data class CinemaDto (
        val id: Int?,
        @NotNull
        val name: String?,
        @NotNull
        val address: String?,
        @NotNull
        val phoneNumber: String?,
        @NotNull
        val postalCode: String?,
        @NotNull
        val director: String?,
        @NotNull
        val nipCode: String?,
        @NotNull
        val buildDate: LocalDate?,
        @NotNull
        val currentState: CinemaState? = CinemaState.OFF,

        val repertoires: MutableList<RepertoireDto?> = ArrayList(),

        val createdAt: LocalDateTime?
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as CinemaDto

                if (id != other.id) return false
                if (name != other.name) return false
                if (address != other.address) return false
                if (phoneNumber != other.phoneNumber) return false
                if (postalCode != other.postalCode) return false
                if (director != other.director) return false
                if (nipCode != other.nipCode) return false
                if (buildDate != other.buildDate) return false
                if (currentState != other.currentState) return false
                if (repertoires != other.repertoires) return false

                return true
        }

        override fun hashCode(): Int {
                var result = id ?: 0
                result = 31 * result + (name?.hashCode() ?: 0)
                result = 31 * result + (address?.hashCode() ?: 0)
                result = 31 * result + (phoneNumber?.hashCode() ?: 0)
                result = 31 * result + (postalCode?.hashCode() ?: 0)
                result = 31 * result + (director?.hashCode() ?: 0)
                result = 31 * result + (nipCode?.hashCode() ?: 0)
                result = 31 * result + (buildDate?.hashCode() ?: 0)
                result = 31 * result + (currentState?.hashCode() ?: 0)
                return result
        }
}

