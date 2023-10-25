package pl.szudor.seating

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import pl.szudor.exception.SeatingNotExistsException

interface SeatingRepository: JpaRepository<Seating, Int>

object SeatingRepositoryExtension {
    fun SeatingRepository.findSeating(id: Int): Seating = this.findByIdOrNull(id) ?: throw SeatingNotExistsException(id)
}