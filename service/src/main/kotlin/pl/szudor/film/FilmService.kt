package pl.szudor.film

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.szudor.exception.FilmNotExistsException
import java.time.LocalDate
import java.time.LocalTime
import javax.transaction.Transactional

interface FilmService {
    fun fetchByFilter(filter: FilmFilter, page: Pageable): Page<Film>
    fun saveFilm(
        playedAt: LocalTime,
        title: String,
        pegi: Pegi,
        duration: Int,
        releaseDate: LocalDate,
        originalLanguage: String
    ): Film

    fun deleteFilm(id: Int)
}

@Service
@Transactional
class FilmServiceImpl(
    private val filmRepository: FilmRepository,
    private val filmFactory: FilmFactory
) : FilmService {
    override fun fetchByFilter(filter: FilmFilter, page: Pageable): Page<Film> =
        filmRepository.fetchByFilter(filter, page)

    override fun saveFilm(
        playedAt: LocalTime,
        title: String,
        pegi: Pegi,
        duration: Int,
        releaseDate: LocalDate,
        originalLanguage: String
    ): Film =
        filmRepository.save(filmFactory.createFilm(playedAt, title, pegi, duration, releaseDate, originalLanguage))

    override fun deleteFilm(id: Int) =
        try {
            filmRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw FilmNotExistsException(id)
        }
}