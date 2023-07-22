package pl.szudor.cinema

interface CinemaService {
    fun getAllCinemas(): List<Cinema>

    fun storeCinema(cinema: CinemaDto): Cinema
//    trying to see if ci works
}