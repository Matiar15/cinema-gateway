package pl.szudor.film

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

interface FilmFactory {
    fun createFilm(
        playedAt: LocalTime,
        title: String,
        pegi: Pegi,
        duration: Int,
        releaseDate: LocalDate,
        originalLanguage: String
    ): Film
}

@Service
class FilmFactoryImpl : FilmFactory {
    override fun createFilm(
        playedAt: LocalTime,
        title: String,
        pegi: Pegi,
        duration: Int,
        releaseDate: LocalDate,
        originalLanguage: String
    ) = Film().apply {
        this.playedAt = playedAt
        this.title = title
        this.pegi = pegi
        this.duration = duration
        this.releaseDate = releaseDate
        this.originalLanguage = originalLanguage
    }
}