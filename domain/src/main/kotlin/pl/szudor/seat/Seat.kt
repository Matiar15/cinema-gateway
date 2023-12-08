package pl.szudor.seat

import pl.szudor.room.Room
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "seat")
class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = 0

    @Column
    var number: Int? = null

    @Column
    var occupied: Occupied? = null

    @ManyToOne
    @JoinColumn(name = "id_room")
    var room: Room? = null

    @Column
    var createdAt: LocalDateTime? = LocalDateTime.now()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Seat

        return id == other.id
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}
enum class Occupied {
    YES, NO
}