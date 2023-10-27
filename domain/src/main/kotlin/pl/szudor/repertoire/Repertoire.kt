package pl.szudor.repertoire

import pl.szudor.cinema.Cinema
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "repertoire")
class Repertoire (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    val id: Int? = 0,

    @Column(name = "played_at")
    val playedAt: LocalDate? = null,

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    var cinema: Cinema? = null
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
