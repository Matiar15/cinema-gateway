package pl.szudor.repertoire

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    var cinema: Cinema?,
)
