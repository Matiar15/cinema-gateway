package pl.szudor.film

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.szudor.exception.FilmNotExistsException
import pl.szudor.repertoire.RepertoireRepository
import pl.szudor.repertoire.findRepertoire
import pl.szudor.repertoire.toDto
import pl.szudor.repertoire.toEntity
import pl.szudor.room.RoomRepository
import pl.szudor.room.findRoom
import pl.szudor.room.toDto
import pl.szudor.room.toEntity
import javax.transaction.Transactional

interface FilmService {
    fun getFilms(page: Pageable): Page<Film>
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
    override fun getFilms(page: Pageable): Page<Film> = filmRepository.findAllFilms(page)

    override fun deleteFilm(id: Int) =
        try {
            filmRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw FilmNotExistsException(id)
        }


    override fun saveFilm(film: FilmDto, repertoireId: Int, roomId: Int): Film =
        filmRepository.save(film.toEntity().apply {
            room = roomRepository.findRoom(roomId)
            repertoire = repertoireRepository.findRepertoire(repertoireId)
        })

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