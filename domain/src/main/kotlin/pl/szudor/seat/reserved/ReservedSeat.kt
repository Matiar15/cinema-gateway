package pl.szudor.seat.reserved

import pl.szudor.event.Event
import pl.szudor.seat.Seat
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.Table

@Entity
@Table(name = "reserved_seat")
class ReservedSeat {
    @Id
    @Column(name = "id_seat")
    var id: Int? = 0

    @JoinColumn(name = "id_seat", insertable = false, updatable = false)
    @ManyToOne
    var seat: Seat? = null

    @JoinColumn(name = "id_event")
    @ManyToOne
    var event: Event? = null

    @Column
    var createdAt: LocalDateTime? = LocalDateTime.now()
}