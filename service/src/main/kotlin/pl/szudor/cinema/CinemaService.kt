package pl.szudor.cinema

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

interface CinemaService {
    fun saveCinema(cinema: CinemaDto): Cinema
    fun getCinemas(page: Pageable): Page<Cinema>
    fun updateState(id: Int, cinemaPayload: CinemaPayload): Cinema
    fun updateCinema(id: Int, cinema: CinemaDto): Cinema
}

@Service
@Transactional
class CinemaServiceImpl(
    private val cinemaRepository: CinemaRepository
) : CinemaService {
    override fun saveCinema(cinema: CinemaDto): Cinema =
        cinemaRepository.save(cinema.apply { currentState = CinemaState.OFF }.toEntity())


    override fun getCinemas(page: Pageable): Page<Cinema> = cinemaRepository.findAllCinemas(page)

    override fun updateState(id: Int, cinemaPayload: CinemaPayload): Cinema =
        cinemaRepository.save(cinemaRepository.findCinema(id).apply { currentState = cinemaPayload.cinemaState })


    override fun updateCinema(id: Int, cinema: CinemaDto): Cinema =
        cinemaRepository.save(cinemaRepository.findCinema(id).apply {
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

fun CinemaDto.toEntity() =
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


fun Cinema.toDto() =
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