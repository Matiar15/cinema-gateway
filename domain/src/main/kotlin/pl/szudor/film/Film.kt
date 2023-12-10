package pl.szudor.film

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "film")
class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Int? = 0

    @Column
    var playedAt: LocalTime? = null

    @Column
    var title: String? = null

    @Column
    @Enumerated(EnumType.STRING)
    var pegi: Pegi? = null

    @Column
    var duration: Int? = null

    @Column
    var releaseDate: LocalDate? = null

    @Column
    var originalLanguage: String? = null

    @Column
    var createdAt: LocalDateTime? = LocalDateTime.now()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Film

        return id == other.id
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}

enum class Pegi {
    THREE,
    SEVEN,
    TWELVE,
    SIXTEEN,
    EIGHTEEN
}