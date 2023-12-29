package pl.szudor.repertoire

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.requireById
import pl.szudor.exception.RepertoireAlreadyPlayedAtException
import java.time.LocalDate


interface RepertoireService {
    fun createRepertoire(cinemaId: Int, playedAt: LocalDate): Repertoire
    fun fetchByFilter(cinemaId: Int, filter: RepertoireFilter, pageRequest: Pageable): Page<Repertoire>
    fun patchRepertoire(id: Int, playedAt: LocalDate): Repertoire
}

@Service
@Transactional
class RepertoireServiceImpl(
    private val repertoireRepository: RepertoireRepository,
    private val repertoireFactory: RepertoireFactory,
    private val cinemaRepository: CinemaRepository,
) : RepertoireService {
    override fun createRepertoire(cinemaId: Int, playedAt: LocalDate): Repertoire =
        try {
            repertoireRepository.save(
                repertoireFactory.createRepertoire(
                    cinemaRepository.requireById(cinemaId),
                    playedAt
                )
            )
        } catch (_: DataIntegrityViolationException) {
            throw RepertoireAlreadyPlayedAtException(playedAt)
        }

    override fun fetchByFilter(cinemaId: Int, filter: RepertoireFilter, pageRequest: Pageable): Page<Repertoire> =
        repertoireRepository.fetchByFilter(cinemaId, filter, pageRequest)

    override fun patchRepertoire(id: Int, playedAt: LocalDate): Repertoire =
        repertoireRepository.findOneByPlayedAt(playedAt)?.let { throw RepertoireAlreadyPlayedAtException(playedAt) }
            ?: repertoireRepository.requireById(id).also { it.playedAt = playedAt }

}