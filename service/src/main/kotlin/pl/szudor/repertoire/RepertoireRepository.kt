package pl.szudor.repertoire

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import pl.szudor.querydsl.applyPagination
import pl.szudor.exception.RepertoireNotExistsException

interface RepertoireRepository : JpaRepository<Repertoire, Int>, RepertoireCustomRepository

fun RepertoireRepository.findRepertoire(id: Int): Repertoire =
    this.findByIdOrNull(id) ?: throw RepertoireNotExistsException(id)

interface RepertoireCustomRepository {
    fun findAllRepertoires(page: Pageable): Page<Repertoire>
}

@Repository
class RepertoireCustomRepositoryImpl : RepertoireCustomRepository, QuerydslRepositorySupport(Repertoire::class.java) {
    override fun findAllRepertoires(page: Pageable): Page<Repertoire> {
        val query = from(QRepertoire.repertoire)
        return querydsl!!.applyPagination(page, query) { query.fetchCount() }
    }

}