package pl.szudor.room

import pl.szudor.cinema.Cinema
import java.time.LocalDateTime
import javax.persistence.*


@Entity(name = "room")
class Room(
    @Column(name = "room_number")
    var roomNumber: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "cinema_id")
    var cinema: Cinema? = null
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

        other as Room

        return id == other.id
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}
