package pl.szudor.film

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.szudor.exception.FilmNotExistsException
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.repertoire.toDto
import pl.szudor.repertoire.toEntity
import pl.szudor.room.RoomRepository
import pl.szudor.room.toDto
import pl.szudor.room.toEntity
import javax.transaction.Transactional

interface FilmService {
    fun getFilms(): List<Film>
    fun deleteFilm(id: Int)
    fun saveFilm(film: FilmDto, roomId: Int): Film
}

@Service
@Transactional
class FilmServiceImpl(
    private val filmRepository: FilmRepository,
    private val roomRepository: RoomRepository
): FilmService {

    @set:Autowired
    lateinit var logger: Logger

    override fun getFilms(): List<Film>
        = filmRepository.findAll()

    override fun deleteFilm(id: Int) {
        try {
            logger.info("DELETING FILM UNDER ID: $id...")
            filmRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw FilmNotExistsException("FILM UNDER ID: $id DOES NOT EXIST", e)
        }
    }

    override fun saveFilm(film: FilmDto, roomId: Int): Film {
        logger.info("SAVING FILM IN ROOM NUMBER: $roomId...")
        return filmRepository.save(film.toEntity().apply {
            room = roomRepository.findByIdOrNull(roomId) ?: throw RoomNotExistsException(roomId.toString())
        })
    }
}

fun Film.toDto() =
    FilmDto(
        id,
        playedAt,
        repertoires.map { it!!.toDto() },
        title,
        pegi,
        duration,
        releaseDate,
        originalLanguage,
        room?.toDto(),
        createdAt,

    )

fun FilmDto.toEntity() =
    Film(
        id,
        playedAt!!,
        repertoires.map { it?.toEntity() },
        title!!,
        pegi!!,
        duration!!,
        releaseDate!!,
        originalLanguage!!,
        room?.toEntity()
    )