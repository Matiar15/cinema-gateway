package pl.szudor.seating

import org.springframework.data.jpa.repository.JpaRepository

interface SeatingRepository: JpaRepository<Seating, Int>
