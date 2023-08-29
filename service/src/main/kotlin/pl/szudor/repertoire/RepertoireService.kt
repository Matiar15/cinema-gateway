package pl.szudor.repertoire

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.toCinema
import pl.szudor.cinema.toDto
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.exception.RepertoireNotExistsException
import pl.szudor.film.FilmRepository
import javax.transaction.Transactional


interface RepertoireService  {
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

    @set:Autowired
    lateinit var logger: Logger

    override fun saveRepertoire(repertoire: RepertoireDto, cinemaId: Int): Repertoire {
        logger.info("SAVING REPERTOIRE")
        return repertoireRepository.save(
                repertoire
                    .toEntity()
                    .apply {
                        logger.info("FINDING CINEMA UNDER ID: $cinemaId")
                        cinema = cinemaRepository.findByIdOrNull(cinemaId)
                            ?: throw CinemaNotExistsException("Cinema under ID: $cinemaId was not found.")
                    }
        )
    }

    override fun getRepertoires(): List<Repertoire>
        = repertoireRepository.findAll()


    override fun deleteRepertoire(id: Int) {
        try {
            logger.info("DELETING FILMS UNDER REPERTOIRE ID: $id")
            filmRepository.deleteAllByRepertoireId(id)
            logger.info("DELETING REPERTOIRE UNDER ID: $id")
            repertoireRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw RepertoireNotExistsException("REPERTOIRE NOT FOUND UNDER ID: $id", e)
        }
    }

}

fun Repertoire.toDto() =
    RepertoireDto(
        id,
        whenPlayed,
        cinema!!.toDto(),
        createdAt
    )

fun RepertoireDto.toEntity() =
    Repertoire(
        id,
        whenPlayed!!,
        cinema?.toCinema(cinema!!.currentState!!)
    )
