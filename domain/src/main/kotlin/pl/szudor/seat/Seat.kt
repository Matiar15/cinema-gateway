package pl.szudor.seat

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "seating")
class Seating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    var id: Int? = 0,

    @Column(name = "number")
    var number: Int? = null,

    ) {
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