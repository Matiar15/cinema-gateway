package pl.szudor.cinema

import com.google.common.collect.Range
import java.time.LocalDate
import java.time.LocalDateTime

data class CinemaFilter(
    val name: String?,
    val address: String?,
    val email: String?,
    val phoneNumber: String?,
    val postalCode: String?,
    val director: String?,
    val nipCode: String?,
    val buildDate: Range<LocalDate>?,
    val state: State?,
    val createdAt: Range<LocalDateTime>?

)
