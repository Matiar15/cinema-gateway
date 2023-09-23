package pl.szudor.cinema

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.repertoire.RepertoireRepository
import javax.transaction.Transactional

interface CinemaService {
    fun saveCinema(cinema: CinemaDto): Cinema
    fun getCinemas(): List<Cinema>

    fun deleteCinema(id: Int)
    fun updateCinema(id: Int, cinema: CinemaDto): Cinema
}

@Service
@Transactional
class CinemaServiceImpl(
    private val cinemaRepository: CinemaRepository,
    private val repertoireRepository: RepertoireRepository,
): CinemaService {

    @set:Autowired
    lateinit var logger: Logger

    override fun saveCinema(cinema: CinemaDto): Cinema {
        logger.info("SAVING CINEMA..")
        return cinemaRepository.save(cinema.apply { currentState = CinemaState.OFF }.toEntity())
    }

    override fun getCinemas(): List<Cinema>
        = cinemaRepository.findAll()

    override fun deleteCinema(id: Int) {
        try {
            logger.info("DELETING FILMS UNDER REPERTOIRE-CINEMA ID: $id...\nDELETING REPERTOIRE UNDER CINEMA ID: $id...")
            repertoireRepository.deleteAllByCinemaId(id)
            logger.info("DELETING CINEMA UNDER ID: $id")
            cinemaRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw CinemaNotExistsException("CINEMA UNDER ID: $id WAS NOT FOUND", e)
        }
    }

    override fun updateCinema(id: Int, cinema: CinemaDto): Cinema {
        val foundCinema = cinemaRepository.findByIdOrNull(id) ?: throw CinemaNotExistsException("CINEMA UNDER ID: $id WAS NOT FOUND")
        return cinemaRepository.save(foundCinema.apply {
                cinema.address?.let { this.address = cinema.address }
                cinema.buildDate?.let { this.buildDate = cinema.buildDate }
                cinema.currentState?.let { this.currentState = cinema.currentState!! }
                cinema.director?.let { this.director = cinema.director }
                cinema.email?.let { this.email = cinema.email }
                cinema.name?.let { this.name = cinema.name }
                cinema.nipCode?.let { this.nipCode = cinema.nipCode }
                cinema.phoneNumber?.let { this.phoneNumber = cinema.phoneNumber }
                cinema.postalCode?.let { this.postalCode = cinema.postalCode }
            }
        )
    }
}

fun CinemaDto.toEntity(): Cinema =
    Cinema(
        id = id,
        name = name!!,
        address = address!!,
        email = email!!,
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
        email,
        phoneNumber,
        postalCode,
        director,
        nipCode,
        buildDate,
        currentState,
        createdAt
    )