package pl.szudor.repertoire

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.toEntity
import pl.szudor.cinema.toDto
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.exception.RepertoireNotExistsException
import pl.szudor.film.*
import javax.transaction.Transactional


interface RepertoireService  {
    fun saveRepertoire(repertoire: RepertoireDto, cinemaId: Int): Repertoire
    fun getRepertoires(): List<Repertoire>
    fun deleteRepertoire(id: Int)
    fun saveFilmUnderRepertoire(film: FilmDto, repertoireId: Int): Repertoire
}

@Service
@Transactional
class RepertoireServiceImpl(
    private val repertoireRepository: RepertoireRepository,
    private val cinemaRepository: CinemaRepository
) : RepertoireService {

    @set:Autowired
    lateinit var logger: Logger

    override fun saveRepertoire(repertoire: RepertoireDto, cinemaId: Int): Repertoire {
        logger.info("SAVING REPERTOIRE...")
        return repertoireRepository.save(
                repertoire
                    .toEntity()
                    .apply {
                        logger.info("FINDING CINEMA UNDER ID: $cinemaId...")
                        cinema = cinemaRepository.findByIdOrNull(cinemaId)
                            ?: throw CinemaNotExistsException("CINEMA UNDER ID: $cinemaId WAS NOT FOUND")
                    }
        )
    }

    override fun getRepertoires(): List<Repertoire>
        = repertoireRepository.findAll()


    override fun deleteRepertoire(id: Int) {
        try {
            logger.info("DELETING FILMS UNDER REPERTOIRE ID: $id...\nDELETING REPERTOIRE UNDER CINEMA ID: $id...")
            repertoireRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw RepertoireNotExistsException("REPERTOIRE NOT FOUND UNDER ID: $id", e)
        }
    }

    override fun saveFilmUnderRepertoire(film: FilmDto, repertoireId: Int): Repertoire {
        val repertoire: Repertoire = repertoireRepository.findByIdOrNull(repertoireId) ?: throw RuntimeException("ASDASDASD") // todo
        return repertoireRepository.save(repertoire.apply { this.film = film.toEntity() } )
    }

}

fun Repertoire.toDto(): RepertoireDto =
    RepertoireDto(
        id,
        playedAt,
        cinema!!.toDto(),
        film?.toDto(),
        createdAt
    )

fun RepertoireDto.toEntity(): Repertoire =
    Repertoire(
        id,
        playedAt!!,
        cinema?.toEntity(),
        film?.toEntity()
    )
