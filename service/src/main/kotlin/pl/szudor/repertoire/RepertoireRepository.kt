package pl.szudor.repertoire

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import pl.szudor.exception.RepertoireNotExistsException

interface RepertoireRepository : JpaRepository<Repertoire, Int>, RepertoireCustomRepository

fun RepertoireRepository.requireById(id: Int): Repertoire =
    this.findByIdOrNull(id) ?: throw RepertoireNotExistsException(id)

interface RepertoireCustomRepository {
    fun findAllRepertoires(page: Pageable): Page<Repertoire>
}

@Repository
class RepertoireCustomRepositoryImpl : RepertoireCustomRepository, QuerydslRepositorySupport(Repertoire::class.java) {
    override fun findAllRepertoires(page: Pageable): Page<Repertoire> {
        val root = from(QRepertoire.repertoire)
        val query = querydsl!!.applyPagination(page, root)
        return PageableExecutionUtils.getPage(query.fetch(), page, query::fetchCount)
    }
}