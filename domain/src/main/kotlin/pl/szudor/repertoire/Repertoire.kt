package pl.szudor.repertoire

import pl.szudor.cinema.Cinema
import pl.szudor.film.Film
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "repertoire")
class Repertoire (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    val id: Int?,
    @Column(name = "played_at")
    val playedAt: LocalDate,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    var cinema: Cinema?,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "film_id")
    var film: Film?
) {
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Repertoire

        return id == other.id
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}
