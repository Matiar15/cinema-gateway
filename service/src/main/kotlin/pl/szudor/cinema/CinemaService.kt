package pl.szudor.cinema

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

interface CinemaService {
    fun saveCinema(cinema: Cinema): Cinema
    fun getCinemas(page: Pageable, filter: CinemaFilter): Page<Cinema>
    fun updateState(id: Int, state: State): Cinema
}

@Service
@Transactional
class CinemaServiceImpl(
    private val cinemaRepository: CinemaRepository
) : CinemaService {
    override fun saveCinema(cinema: Cinema): Cinema =
        cinemaRepository.save(cinema.apply { state = State.NO })


    override fun getCinemas(page: Pageable, filter: CinemaFilter): Page<Cinema> =
        cinemaRepository.fetchByFilter(page, filter)

    override fun updateState(id: Int, state: State): Cinema =
        cinemaRepository.save(cinemaRepository.requireById(id).apply { this.state = state })
}