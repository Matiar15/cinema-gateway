package pl.szudor.film

import org.springframework.stereotype.Service
import java.time.LocalDate

interface FilmFactory {
    fun createFilm(
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
        title: String,
        pegi: Pegi,
        duration: Int,
        releaseDate: LocalDate,
        originalLanguage: String
    ) = Film().apply {
        this.title = title
        this.pegi = pegi
        this.duration = duration
        this.releaseDate = releaseDate
        this.originalLanguage = originalLanguage
    }
}