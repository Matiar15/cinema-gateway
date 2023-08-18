package pl.szudor.film

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.szudor.repertoire.RepertoireRepository
import pl.szudor.repertoire.toDto
import pl.szudor.repertoire.toEntity
import javax.transaction.Transactional

interface FilmService {
    fun saveFilm(film: FilmDto, repertoireId: Int): Film
    fun getFilms(): List<Film>
    fun deleteFilm(id: Int)
}

@Service
@Transactional
class FilmServiceImpl(
    private val filmRepository: FilmRepository,
    private val repertoireRepository: RepertoireRepository
): FilmService {

    @set:Autowired
    lateinit var logger: Logger

    override fun saveFilm(film: FilmDto, repertoireId: Int): Film = filmRepository.save(
        film
            .toEntity()
            .apply {
                logger.info("FINDING REPERTOIRE UNDER ID: $repertoireId")
                repertoire = repertoireRepository.findByIdOrNull(repertoireId)
                    ?: throw RuntimeException()
            })
    override fun getFilms(): List<Film>
        = filmRepository.findAll() ?: throw RuntimeException()
    override fun deleteFilm(id: Int) {
        try {
            filmRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw RuntimeException(e.message)
        }
    }
}

fun Film.toDto() =
    FilmDto(
        id,
        playedAt,
        roomNumber,
        repertoire?.toDto()
    )

fun FilmDto.toEntity() =
    Film(
        id,
        playedAt!!,
        roomNumber!!,
        repertoire?.toEntity()
    )