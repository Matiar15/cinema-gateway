package pl.szudor.repertoire

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import pl.szudor.cinema.CinemaDto
import java.time.LocalDate

data class RepertoireDto (
    val id: Int?,
    val whenPlayed: LocalDate?,
    var cinema: CinemaDto?
)