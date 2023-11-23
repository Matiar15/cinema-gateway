package pl.szudor.cinema

import pl.szudor.utils.RangeDto

data class CinemaFilterDto(
    val name: String?,
    val address: String?,
    val email: String?,
    val phoneNumber: String?,
    val postalCode: String?,
    val director: String?,
    val nipCode: String?,
    /*val buildDate: RangeDto?,*/
    val state: State?,
    /*val createdAt: RangeDto?*/
)
