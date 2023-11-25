package pl.szudor.cinema

import pl.szudor.utils.RangeDateConstraint
import pl.szudor.utils.RangeDateTimeConstraint
import pl.szudor.utils.RangeDto

data class CinemaFilterDto(
    val name: String?,
    val address: String?,
    val email: String?,
    val phoneNumber: String?,
    val postalCode: String?,
    val director: String?,
    val nipCode: String?,
    @field:RangeDateConstraint
    var buildDate: RangeDto.LocalDate?,
    val state: State?,
    @field:RangeDateTimeConstraint
    var createdAt: RangeDto.LocalDateTime?
)