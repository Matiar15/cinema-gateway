package pl.szudor.film

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.szudor.exception.FilmNotExistsException
import java.time.LocalDate

interface FilmService {
    fun fetchByFilter(filter: FilmFilter, page: Pageable): Page<Film>
    fun saveFilm(
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
        title: String,
        pegi: Pegi,
        duration: Int,
        releaseDate: LocalDate,
        originalLanguage: String
    ): Film = filmRepository.save(filmFactory.createFilm(title, pegi, duration, releaseDate, originalLanguage))

    override fun deleteFilm(id: Int) = runCatching {
        filmRepository.deleteById(id)
    }.getOrElse { throw FilmNotExistsException(id) }

}