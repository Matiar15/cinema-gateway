package pl.szudor.seating

import pl.szudor.room.Room
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "seating")
class Seating(
    @Column(name = "seat_number")
    var seatNumber: Int,

    @ManyToOne
    @JoinColumn(name = "room_id")
    var room: Room?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    var id: Int = 0

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Seating

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}