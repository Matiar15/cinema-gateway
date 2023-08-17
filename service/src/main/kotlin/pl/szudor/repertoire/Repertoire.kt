package pl.szudor.repertoire

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import pl.szudor.cinema.Cinema
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "repertoire")
class Repertoire (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    val id: Int?,

    @Column(name = "when_played")
    val whenPlayed: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.DETACH,
        CascadeType.REFRESH]
    )
    @JoinColumn(name = "cinema_id")
    var cinema: Cinema?,
)
