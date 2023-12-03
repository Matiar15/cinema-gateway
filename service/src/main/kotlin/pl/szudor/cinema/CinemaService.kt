package pl.szudor.cinema

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.transaction.Transactional

interface CinemaService {
    fun saveCinema(
        name: String,
        address: String,
        email: String,
        phoneNumber: String,
        postalCode: String,
        director: String,
        nipCode: String,
        buildDate: LocalDate
    ): Cinema

    fun getCinemas(page: Pageable, filter: CinemaFilter): Page<Cinema>
    fun updateState(id: Int, state: State): Cinema
}

@Service
@Transactional
class CinemaServiceImpl(
    private val cinemaRepository: CinemaRepository,
    private val cinemaFactory: CinemaFactory
) : CinemaService {
    override fun saveCinema(
        name: String,
        address: String,
        email: String,
        phoneNumber: String,
        postalCode: String,
        director: String,
        nipCode: String,
        buildDate: LocalDate
    ): Cinema =
        cinemaRepository.save(
            cinemaFactory.createCinema(
                name,
                address,
                email,
                phoneNumber,
                postalCode,
                director,
                nipCode,
                buildDate
            )
        )


    override fun getCinemas(page: Pageable, filter: CinemaFilter): Page<Cinema> =
        cinemaRepository.fetchByFilter(page, filter)

    override fun updateState(id: Int, state: State): Cinema =
        cinemaRepository.save(cinemaRepository.requireById(id).apply { this.state = state })
}