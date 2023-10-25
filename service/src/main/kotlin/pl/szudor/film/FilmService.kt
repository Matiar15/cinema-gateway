package pl.szudor.film

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import pl.szudor.exception.FilmNotExistsException
import pl.szudor.repertoire.RepertoireRepository
import pl.szudor.repertoire.RepertoireRepositoryExtension.findRepertoire
import pl.szudor.repertoire.toDto
import pl.szudor.repertoire.toEntity
import pl.szudor.room.RoomRepository
import pl.szudor.room.RoomRepositoryExtension.findRoom
import pl.szudor.room.toDto
import pl.szudor.room.toEntity
import javax.transaction.Transactional

interface FilmService {
    fun getFilms(): List<Film>
    fun deleteFilm(id: Int)
    fun saveFilm(film: FilmDto, repertoireId: Int, roomId: Int): Film
}

@Service
@Transactional
class FilmServiceImpl(
    private val filmRepository: FilmRepository,
    private val roomRepository: RoomRepository,
    private val repertoireRepository: RepertoireRepository
) : FilmService {

    @Autowired
    lateinit var logger: Logger

    override fun getFilms(): List<Film> = filmRepository.findAll()

    override fun deleteFilm(id: Int) {
        try {
            logger.info("DELETING FILM UNDER ID: $id...")
            filmRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw FilmNotExistsException(id)
        }
    }

    override fun saveFilm(film: FilmDto, repertoireId: Int, roomId: Int): Film {
        logger.info("SAVING FILM IN ROOM NUMBER: $roomId...")
        val savedFilm = film.toEntity().apply {
            room = roomRepository.findRoom(roomId)
            repertoire = repertoireRepository.findRepertoire(repertoireId)
        }

        return filmRepository.save(savedFilm)
    }
}

fun Film.toDto() =
    FilmDto(
        id,
        playedAt,
        repertoire?.toDto(),
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
        repertoire?.toEntity(),
        title!!,
        pegi!!,
        duration!!,
        releaseDate!!,
        originalLanguage!!,
        room?.toEntity()
    )