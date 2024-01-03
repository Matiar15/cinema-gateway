package pl.szudor.seat.reserved

import pl.szudor.event.Event
import pl.szudor.seat.Seat
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.Table

@Entity
@Table(name = "reserved_seat")
class ReservedSeat {
    @EmbeddedId
    var id: ReservedSeatKey? = null

    @JoinColumn(name = "id_seat")
    @ManyToOne
    @MapsId("seatId")
    var seat: Seat? = null

    @JoinColumn(name = "id_event")
    @ManyToOne
    @MapsId("eventId")
    var event: Event? = null

    @Column
    var createdAt: LocalDateTime? = null
}

@Embeddable
class ReservedSeatKey : Serializable {
    @Column(name = "id_seat")
    var eventId: Int? = null
    @Column(name = "id_seat")
    var seatId: Int? = null
}