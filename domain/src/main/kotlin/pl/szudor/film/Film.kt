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
    var id: Int?,

    @Column(name = "played_at")
    val playedAt: LocalTime,

    @OneToMany(mappedBy = "film", cascade = [CascadeType.ALL], orphanRemoval = true)
    var repertoires: List<Repertoire?> = mutableListOf(),

    @Column(name = "title")
    val title: String?,

    @Column(name = "pegi")
    @Enumerated(EnumType.STRING)
    val pegi: Pegi?,

    @Column(name = "duration")
    val duration: Int,

    @Column(name = "release_date")
    val releaseDate: LocalDate?,

    @Column(name = "original_language")
    val originalLanguage: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    var room: Room?
) {
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
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