package pl.szudor.repertoire

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.CinemaRepositoryExtension.findCinema
import pl.szudor.cinema.toDto
import pl.szudor.cinema.toEntity
import pl.szudor.exception.RepertoireNotExistsException
import pl.szudor.film.FilmRepository
import javax.transaction.Transactional


interface RepertoireService {
    fun saveRepertoire(repertoire: RepertoireDto, cinemaId: Int): Repertoire
    fun getRepertoires(): List<Repertoire>
    fun deleteRepertoire(id: Int)
}

@Service
@Transactional
class RepertoireServiceImpl(
    private val repertoireRepository: RepertoireRepository,
    private val cinemaRepository: CinemaRepository,
    private val filmRepository: FilmRepository
) : RepertoireService {

    @Autowired
    lateinit var logger: Logger

    override fun saveRepertoire(repertoire: RepertoireDto, cinemaId: Int): Repertoire {
        logger.info("SAVING REPERTOIRE...")
        return repertoireRepository.save(
            repertoire
                .toEntity()
                .apply { cinema = cinemaRepository.findCinema(cinemaId) }
        )
    }

    override fun getRepertoires(): List<Repertoire> = repertoireRepository.findAll()


    override fun deleteRepertoire(id: Int) {
        try {
            logger.info("DELETING FILMS UNDER REPERTOIRE ID: $id...\nDELETING REPERTOIRE UNDER CINEMA ID: $id...")
            filmRepository.deleteAllByRepertoireId(id)
            repertoireRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw RepertoireNotExistsException(id)
        }
    }

}

fun Repertoire.toDto(): RepertoireDto =
    RepertoireDto(
        id,
        playedAt,
        cinema!!.toDto(),
        createdAt
    )

fun RepertoireDto.toEntity(): Repertoire =
    Repertoire(
        id,
        playedAt!!,
        cinema?.toEntity()
    )
