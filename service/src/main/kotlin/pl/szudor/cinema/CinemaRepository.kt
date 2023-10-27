package pl.szudor.cinema

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import pl.szudor.exception.CinemaNotExistsException

@Repository

interface CinemaRepository: JpaRepository<Cinema, Int>

object CinemaRepositoryExtension {
    fun CinemaRepository.findCinema(id: Int): Cinema = this.findByIdOrNull(id) ?: throw CinemaNotExistsException(id)
}