package pl.szudor.cinema

import org.jetbrains.annotations.NotNull

data class CinemaDto (
        @NotNull
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
)

fun CinemaDto.toCinema(): Cinema {
        return Cinema(
                id!!,
                name!!,
                street!!,
                director!!,
                phoneNumber!!,
                postalCode!!
        )
}