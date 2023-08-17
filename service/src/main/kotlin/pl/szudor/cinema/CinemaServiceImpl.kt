package pl.szudor.cinema

import org.springframework.stereotype.Service
import pl.szudor.repertoire.Repertoire
import pl.szudor.repertoire.RepertoireDto
import pl.szudor.repertoire.toDto
import pl.szudor.repertoire.toEntity

@Service
class CinemaServiceImpl(
    private val cinemaRepository: CinemaRepository
    ): CinemaService {

    override fun getAllCinemas(): List<Cinema> = cinemaRepository.findAll()
    override fun storeCinema(cinema: CinemaDto): Cinema {
        val toCinema = cinema.toCinema()
        if (cinema.repertoires.isEmpty()) return cinemaRepository.save(toCinema)
        toCinema.repertoires.forEach { it!!.cinema = toCinema}
        return cinemaRepository.save(toCinema)
    }

}

fun CinemaDto.toCinema(): Cinema =
    Cinema(
        id = id,
        name = name!!,
        address = address!!,
        director = director!!,
        phoneNumber = phoneNumber!!,
        postalCode = postalCode!!,
        nipCode = nipCode!!,
        buildDate = buildDate!!,
        currentState = currentState!!,
        repertoires = repertoires.map { it?.toEntity() }
    )


fun Cinema.toDto(): CinemaDto =
    CinemaDto(
        id,
        name,
        address,
        phoneNumber,
        postalCode,
        director,
        nipCode,
        buildDate,
        currentState,
        mutableListOf(),
        createdAt
    )