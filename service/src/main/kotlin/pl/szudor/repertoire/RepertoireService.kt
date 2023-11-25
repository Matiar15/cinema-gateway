/*
package pl.szudor.repertoire

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.findCinema
import pl.szudor.cinema.toDto
import pl.szudor.cinema.toEntity
import pl.szudor.exception.RepertoireNotExistsException
import pl.szudor.film.FilmRepository
import javax.transaction.Transactional


interface RepertoireService {
    fun saveRepertoire(repertoire: RepertoireDto, cinemaId: Int): Repertoire
    fun getAll(page: Pageable): Page<Repertoire>
    fun deleteRepertoire(id: Int)
}

@Service
@Transactional
class RepertoireServiceImpl(
    private val repertoireRepository: RepertoireRepository,
    private val cinemaRepository: CinemaRepository,
    private val filmRepository: FilmRepository
) : RepertoireService {
    override fun saveRepertoire(repertoire: RepertoireDto, cinemaId: Int): Repertoire =
        repertoireRepository.save(
            repertoire
                .toEntity()
                .apply { cinema = cinemaRepository.findCinema(cinemaId) }
        )


    override fun getAll(page: Pageable): Page<Repertoire> = repertoireRepository.findAllRepertoires(page)


    override fun deleteRepertoire(id: Int) =
        try {
            repertoireRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw RepertoireNotExistsException(id)
        }


}

fun Repertoire.toDto() =
    RepertoireDto(
        id,
        playedAt,
        cinema!!.toDto(),
        createdAt
    )

fun RepertoireDto.toEntity() =
    Repertoire(
        id,
        playedAt!!,
        cinema?.toEntity()
    )
*/
