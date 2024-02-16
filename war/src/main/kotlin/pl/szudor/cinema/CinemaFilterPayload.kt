package pl.szudor.cinema

import pl.szudor.utils.RangeDate
import pl.szudor.utils.RangeDateTime
import pl.szudor.utils.RangeDto

data class CinemaFilterPayload(
    val name: String?,
    val address: String?,
    val email: String?,
    val phoneNumber: String?,
    val postalCode: String?,
    val director: String?,
    val nipCode: String?,
    @field:RangeDate
    var buildDate: RangeDto.LocalDate?,
    val active: Boolean?,
    @field:RangeDateTime
    var createdAt: RangeDto.LocalDateTime?
)