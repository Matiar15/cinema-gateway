package pl.szudor.film

import org.springframework.stereotype.Service
import pl.szudor.repertoire.toDto
import pl.szudor.repertoire.toEntity

interface FilmService {
    fun saveFilm(film: FilmDto): Film
    fun deleteFilm(id: Int)
}

@Service
class FilmServiceImpl(
    private val filmRepository: FilmRepository
): FilmService {
    override fun saveFilm(film: FilmDto): Film = filmRepository.save(film.toEntity())

    override fun deleteFilm(id: Int) {
        filmRepository.deleteById(id)
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