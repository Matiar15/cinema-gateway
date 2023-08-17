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
    var repertoire: Repertoire?


)
