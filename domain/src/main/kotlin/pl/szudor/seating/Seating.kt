package pl.szudor.seating

import pl.szudor.room.Room
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "seating")
class Seating(
    @Column(name = "seat_number")
    var seatNumber: Int? = null,

    @ManyToOne
    @JoinColumn(name = "room_id")
    var room: Room? = null,

    @Column(name = "is_taken")
    @Enumerated(EnumType.STRING)
    var isTaken: Taken? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    var id: Int? = 0

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Seating

        return id == other.id
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}
enum class Taken {
    YES,
    NO
}