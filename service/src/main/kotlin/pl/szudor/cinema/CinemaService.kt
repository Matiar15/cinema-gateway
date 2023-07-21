package pl.szudor.cinema

interface CinemaService {
    fun getAllCinemas(): List<Cinema>

    fun storeCinema(cinema: Cinema)
}