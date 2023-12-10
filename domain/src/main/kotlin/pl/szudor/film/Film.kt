package pl.szudor.film

import pl.szudor.repertoire.Repertoire
import pl.szudor.room.Room
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "film")
class Film (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    var id: Int? = 0,

    @Column(name = "played_at")
    val playedAt: LocalTime? = null,

    @ManyToOne
    @JoinColumn(name = "repertoire_id", referencedColumnName = "id")
    var repertoire: Repertoire? = null,

    @Column(name = "title")
    val title: String? = null,

    @Column(name = "pegi")
    @Enumerated(EnumType.STRING)
    val pegi: Pegi? = null,

    @Column(name = "duration")
    val duration: Int? = null,

    @Column(name = "release_date")
    val releaseDate: LocalDate? = null,

    @Column(name = "original_language")
    val originalLanguage: String? = null,

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    var room: Room? = null
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