package pl.szudor.repertoire

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import pl.szudor.exception.RepertoireNotExistsException

@Repository
interface RepertoireRepository : JpaRepository<Repertoire, Int> {
    fun deleteAllByCinemaId(cinemaId: Int)
}

object RepertoireRepositoryExtension {
    fun RepertoireRepository.findRepertoire(id: Int): Repertoire =
        this.findByIdOrNull(id) ?: throw RepertoireNotExistsException(id)
}
