package pl.szudor.film

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FilmRepository: JpaRepository<Film, Int> {
    fun deleteAllByRoomId(roomId: Int)
    fun deleteAllByRepertoireId(repertoireId: Int)
}