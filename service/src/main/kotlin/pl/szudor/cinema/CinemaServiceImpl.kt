package pl.szudor.cinema

import org.springframework.stereotype.Service

@Service
class CinemaServiceImpl(
    private val cinemaRepository: CinemaRepository
    ): CinemaService {

    override fun getAllCinemas(): List<Cinema> = cinemaRepository.findAll()
    override fun storeCinema(cinema: CinemaDto): Cinema = cinemaRepository.save(cinema.toCinema())

}

fun CinemaDto.toCinema(): Cinema {
    return Cinema(
        id = id,
        name = name!!,
        street = street!!,
        director = director!!,
        phoneNumber = phoneNumber!!,
        postalCode = postalCode!!
    )
}

fun Cinema.toDto(): CinemaDto {
    return CinemaDto(
        id,
        name,
        street,
        phoneNumber,
        postalCode,
        director,
        createdAt
    )
}