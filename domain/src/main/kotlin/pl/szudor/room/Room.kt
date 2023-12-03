package pl.szudor.room

import pl.szudor.cinema.Cinema
import java.time.LocalDateTime
import javax.persistence.*


@Entity(name = "room")
class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = 0

    @Column
    var number: Int? = null

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    var cinema: Cinema? = null

    @Column
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
