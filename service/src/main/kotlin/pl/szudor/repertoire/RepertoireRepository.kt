package pl.szudor.repertoire

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RepertoireRepository: JpaRepository<Repertoire, Int> {
    fun deleteAllByCinemaId(cinemaId: Int)
}
