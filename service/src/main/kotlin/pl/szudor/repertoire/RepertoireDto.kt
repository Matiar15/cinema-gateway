package pl.szudor.repertoire

import pl.szudor.cinema.CinemaDto
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

data class RepertoireDto (
    val id: Int?,
    @field:NotNull
    val whenPlayed: LocalDate?,

    var cinema: CinemaDto?,

    val createdAt: LocalDateTime?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RepertoireDto

        if (id != other.id) return false
        if (whenPlayed != other.whenPlayed) return false
        if (cinema != other.cinema) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (whenPlayed?.hashCode() ?: 0)
        result = 31 * result + (cinema?.hashCode() ?: 0)
        return result
    }
}