package pl.szudor.film

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "film")
class Film(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    var id: Int? = 0,

    @Column(name = "played_at")
    var playedAt: LocalTime? = null,

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "pegi")
    @Enumerated(EnumType.STRING)
    var pegi: Pegi? = null,

    @Column(name = "duration")
    var duration: Int? = null,

    @Column(name = "release_date")
    var releaseDate: LocalDate? = null,

    @Column(name = "original_language")
    var originalLanguage: String? = null,

    @Column(name = "room_number")
    var roomNumber: Int? = null
) {
    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()
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