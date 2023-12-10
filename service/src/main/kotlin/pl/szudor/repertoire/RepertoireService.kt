package pl.szudor.repertoire

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.requireById
import pl.szudor.exception.RepertoireAlreadyPlayedAtException
import pl.szudor.exception.RepertoireNotExistsException
import java.time.LocalDate
import javax.transaction.Transactional


interface RepertoireService {
    fun createRepertoire(cinemaId: Int, playedAt: LocalDate): Repertoire
    fun fetchByFilter(cinemaId: Int, filter: RepertoireFilter, pageRequest: Pageable): Page<Repertoire>
    fun patchRepertoire(id: Int, playedAt: LocalDate): Repertoire
    fun deleteRepertoire(id: Int)
}

@Service
@Transactional
class RepertoireServiceImpl(
    private val repertoireRepository: RepertoireRepository,
    private val repertoireFactory: RepertoireFactory,
    private val cinemaRepository: CinemaRepository,
) : RepertoireService {
    override fun createRepertoire(cinemaId: Int, playedAt: LocalDate): Repertoire =
        repertoireRepository.save(
            repertoireFactory.createRepertoire(
                cinemaRepository.requireById(cinemaId),
                playedAt
            )
        )

    override fun fetchByFilter(cinemaId: Int, filter: RepertoireFilter, pageRequest: Pageable): Page<Repertoire> =
        repertoireRepository.fetchByFilter(cinemaId, filter, pageRequest)

    override fun patchRepertoire(id: Int, playedAt: LocalDate): Repertoire =
        repertoireRepository.findOneByPlayedAt(playedAt)?.let { throw RepertoireAlreadyPlayedAtException(playedAt) }
            ?: repertoireRepository.requireById(id).also { it.playedAt = playedAt }

    override fun deleteRepertoire(id: Int): Unit = runCatching {
        repertoireRepository.deleteById(id)
    }.getOrElse { throw RepertoireNotExistsException(id) }

}