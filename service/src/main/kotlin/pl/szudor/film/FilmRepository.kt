package pl.szudor.film

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FilmRepository: JpaRepository<Film, Int> {
    fun deleteAllByRepertoireId(repertoireId: Int)
    fun deleteAllByRepertoireCinemaId(cinemaId: Int)
}