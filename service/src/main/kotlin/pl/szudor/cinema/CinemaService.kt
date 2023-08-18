package pl.szudor.cinema

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import pl.szudor.film.FilmRepository
import pl.szudor.repertoire.RepertoireRepository
import javax.transaction.Transactional

interface CinemaService {
    fun saveCinema(cinema: CinemaDto): Cinema
    fun getCinemas(): List<Cinema>

    fun deleteCinema(id: Int)
}

@Service
@Transactional
class CinemaServiceImpl(
    private val cinemaRepository: CinemaRepository,
    private val repertoireRepository: RepertoireRepository,
    private val filmRepository: FilmRepository
): CinemaService {

    @set:Autowired
    lateinit var logger: Logger

    override fun saveCinema(cinema: CinemaDto): Cinema
            = cinemaRepository.save(cinema.toCinema())

    override fun getCinemas(): List<Cinema>
        = cinemaRepository.findAll() ?: throw RuntimeException()

    override fun deleteCinema(id: Int) {
        try {
            logger.info("DELETING FILMS UNDER REPERTOIRE-CINEMA ID: $id ")
            filmRepository.deleteAllByRepertoireCinemaId(id)
            logger.info("DELETING REPERTOIRE UNDER CINEMA ID: $id")
            repertoireRepository.deleteAllByCinemaId(id)
            logger.info("DELETING CINEMA UNDER ID: $id")
            cinemaRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw RuntimeException(e.message)
        }
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
        currentState = currentState!!
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
        createdAt
    )