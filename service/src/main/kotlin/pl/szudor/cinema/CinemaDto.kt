package pl.szudor.cinema

import org.jetbrains.annotations.NotNull

data class CinemaDto (
        @field:NotNull
        val name: String? = null,
        @field:NotNull
        val street: String? = null,
        @field:NotNull
        val director: String? = null,
        @field:NotNull
        val phoneNumber: String? = null,
        @field:NotNull
        val postalCode: String? = null,
) {

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as CinemaDto

                if (name != other.name) return false
                if (street != other.street) return false
                if (director != other.director) return false
                if (phoneNumber != other.phoneNumber) return false
                return postalCode == other.postalCode
        }

        override fun hashCode(): Int {
                var result = name?.hashCode() ?: 0
                result = 31 * result + (street?.hashCode() ?: 0)
                result = 31 * result + (director?.hashCode() ?: 0)
                result = 31 * result + (phoneNumber?.hashCode() ?: 0)
                result = 31 * result + (postalCode?.hashCode() ?: 0)
                return result
        }
}
fun CinemaDto.toCinema(): Cinema {
        return Cinema(
                name = name,
                street = street,
                director = director,
                phoneNumber = phoneNumber,
                postalCode = postalCode
        )
}