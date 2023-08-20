package pl.szudor.film

import pl.szudor.repertoire.Repertoire
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

    @Column(name = "room_number")
    val roomNumber: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repertoire_id")
    var repertoire: Repertoire?,

    /*@Column(name = "title")
    val title: String?,

    @Column(name = "pegi")
    val pegi: Pegi?,

    @Column(name = "director")
    val director: Person?,


    @ManyToMany
    @JoinTable(
        name = "",
        joinColumns = @JoinColumn(name = "id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    val cast: List<Person?>,

    val duration: Int,

    val releaseDate: LocalDate?,

    val originalLanguage: String?,

    val production: String?*/
) {
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
