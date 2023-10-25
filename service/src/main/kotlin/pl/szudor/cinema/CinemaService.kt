package pl.szudor.cinema

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.szudor.cinema.CinemaRepositoryExtension.findCinema
import javax.transaction.Transactional

interface CinemaService {
    fun saveCinema(cinema: CinemaDto): Cinema
    fun getCinemas(): List<Cinema>
    fun updateState(id: Int, cinemaPayload: CinemaPayload): Cinema
    fun updateCinema(id: Int, cinema: CinemaDto): Cinema
}

@Service
@Transactional
class CinemaServiceImpl(
    private val cinemaRepository: CinemaRepository
) : CinemaService {

    @set:Autowired
    lateinit var logger: Logger

    override fun saveCinema(cinema: CinemaDto): Cinema {
        logger.info("SAVING CINEMA..")
        return cinemaRepository.save(cinema.apply { currentState = CinemaState.OFF }.toEntity())
    }

    override fun getCinemas(): List<Cinema> = cinemaRepository.findAll()

    override fun updateState(id: Int, cinemaPayload: CinemaPayload): Cinema {
        logger.info("HIDING CINEMA UNDER ID: $id")
        return cinemaRepository.save(cinemaRepository.findCinema(id).apply { currentState = cinemaPayload.cinemaState })
    }

    override fun updateCinema(id: Int, cinema: CinemaDto): Cinema {
        val foundCinema = cinemaRepository.findCinema(id)
        return cinemaRepository.save(foundCinema.apply {
            cinema.address?.let { this.address = cinema.address }
            cinema.buildDate?.let { this.buildDate = cinema.buildDate }
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