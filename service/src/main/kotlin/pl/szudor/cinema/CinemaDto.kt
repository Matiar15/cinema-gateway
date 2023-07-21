package pl.szudor.cinema

import org.jetbrains.annotations.NotNull

data class CinemaDto (
        val id: Long,
        @field:NotNull
        val name: String,
        @field:NotNull
        val street: String,
        @field:NotNull
        val director: String,
        @field:NotNull
        val phoneNumber: String,
        @field:NotNull
        val postalCode: String,
) {

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as CinemaDto

                if (id != other.id) return false
                if (name != other.name) return false
                if (street != other.street) return false
                if (director != other.director) return false
                if (phoneNumber != other.phoneNumber) return false
                return postalCode == other.postalCode
        }

        override fun hashCode(): Int {
                var result = id.hashCode()
                result = 31 * result + name.hashCode()
                result = 31 * result + street.hashCode()
                result = 31 * result + director.hashCode()
                result = 31 * result + phoneNumber.hashCode()
                result = 31 * result + postalCode.hashCode()
                return result
        }
}
fun CinemaDto.toCinema(): Cinema {
        return Cinema(
                id = id,
                name = name,
                street = street,
                director = director,
                phoneNumber = phoneNumber,
                postalCode = postalCode
        )
}