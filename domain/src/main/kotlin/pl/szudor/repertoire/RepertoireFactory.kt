package pl.szudor.repertoire

import org.springframework.stereotype.Service
import pl.szudor.cinema.Cinema
import java.time.LocalDate
import java.time.LocalDateTime

interface RepertoireFactory {
    fun createRepertoire(cinema: Cinema, playedAt: LocalDate): Repertoire
}

@Service
class RepertoireFactoryImpl: RepertoireFactory {
    override fun createRepertoire(cinema: Cinema, playedAt: LocalDate) =
        Repertoire().apply {
            this.cinema = cinema
            this.playedAt = playedAt
            this.createdAt = LocalDateTime.now()
        }
}