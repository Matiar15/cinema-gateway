package pl.szudor.film

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.szudor.exception.FilmNotExistsException
import pl.szudor.repertoire.*
import javax.transaction.Transactional

interface FilmService {
    fun getAll(page: Pageable): Page<Film>
    fun saveFilm(filter: FilmFilter): Film
    fun updateFilm(id: Int, filter: FilmFilter): Film
    fun patchFilm(id: Int, filter: FilmFilter): Film
    fun deleteFilm(id: Int)
}

@Service
@Transactional
class FilmServiceImpl(
    private val filmRepository: FilmRepository,
    private val repertoireRepository: RepertoireRepository
) : FilmService {
    override fun getAll(page: Pageable): Page<Film> = filmRepository.findAllFilms(page)
    override fun saveFilm(filter: FilmFilter): Film =
        filmRepository.save(
            filter.toEntity()
        )

    override fun updateFilm(id: Int, filter: FilmFilter): Film =
        filmRepository.save(filmRepository.requireById(id).apply {
            playedAt = filter.playedAt
            title = filter.title
            duration = filter.duration
            releaseDate = filter.releaseDate
            originalLanguage = filter.originalLanguage
        }
        )


    override fun patchFilm(id: Int, filter: FilmFilter): Film =
        filmRepository.save(filmRepository.requireById(id))

    override fun deleteFilm(id: Int) =
        try {
            filmRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw FilmNotExistsException(id)
        }
}

fun FilmFilter.toEntity() =
    Film(
        playedAt = playedAt,
        title = title,
        pegi = pegi,
        duration = duration,
        releaseDate = releaseDate,
        originalLanguage = originalLanguage
    )
