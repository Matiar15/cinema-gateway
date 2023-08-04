package pl.szudor.cinema

import org.springframework.stereotype.Service

@Service
class CinemaServiceImpl(
    private val cinemaRepository: CinemaRepository
    ): CinemaService {

    override fun getAllCinemas(): List<Cinema> {
        return cinemaRepository.findAll()
    }

    override fun storeCinema(cinema: CinemaDto): Cinema = cinemaRepository.save(cinema.toCinema())
}