package pl.szudor.seat.reserved

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import pl.szudor.event.Event
import pl.szudor.event.EventRepository
import pl.szudor.exception.EventNotExistsException
import pl.szudor.exception.SeatNotExistsException
import pl.szudor.film.Film
import pl.szudor.repertoire.Repertoire
import pl.szudor.room.Room
import pl.szudor.seat.Seat
import pl.szudor.seat.SeatRepository
import spock.lang.Specification

import java.time.LocalTime

class ReservedSeatServiceImplTest extends Specification {
    ReservedSeatRepository reservedSeatRepository = Mock()
    SeatRepository seatRepository = Mock()
    EventRepository eventRepository = Mock()
    ReservedSeatFactory reservedSeatFactory = Mock()

    def underTest = new ReservedSeatServiceImpl(reservedSeatRepository, seatRepository, eventRepository, reservedSeatFactory)

    def room_ = new Room().tap{ id = 4}

    def event_ = new Event().tap {
        id = 1
        film = new Film().tap {id = 1}
        playedAt = LocalTime.of(22, 35)
        repertoire = new Repertoire().tap {id = 3}
        room = room_
    }

    def seat_ = new Seat().tap {
        id = 2
        room = room_
        number = 3
    }

    def "create without found event"() {
        when:
        underTest.create(1, 2)

        then: "repository method was invoked"
        1 * eventRepository.findById(1) >> Optional.empty()

        and: "the exception was thrown and no other interactions"
        thrown EventNotExistsException
        0 * _
    }

    def "create without found seat"() {
        when:
        underTest.create(1, 2)

        then: "repository method was invoked"
        1 * eventRepository.findById(1) >> Optional.of(event_)

        and: "repository method was invoked"
        1 * seatRepository.findById(2) >> Optional.empty()

        and: "the exception was thrown and no other interactions"
        thrown SeatNotExistsException
        0 * _
    }

    def "create reserved seat all good"() {
        given:
        def reservedSeat = new ReservedSeat().tap {
            id = seat_.id = 1
            event = event_
            seat = seat_
        }

        when:
        underTest.create(1, 2)

        then: "repository method was invoked"
        1 * eventRepository.findById(1) >> Optional.of(event_)

        and: "repository method was invoked"
        1 * seatRepository.findById(2) >> Optional.of(seat_)

        and: "factory method was invoked"
        1 * reservedSeatFactory.create(event_, seat_)
            >> reservedSeat

        and: "repository method was invoked"
        1 * reservedSeatRepository.save(reservedSeat) >> reservedSeat

        and: "no other interactions"
        0 * _
    }

    def "remove reserved seat all good"() {
        when:
        underTest.delete(1)

        then: "repository method was invoked"
        1 * reservedSeatRepository.deleteById(1)

        and: "no other interactions"
        0 * _
    }

    def "fetch reserved seat all good"() {
        given:
        def pageable = Mock(Pageable)

        when:
        underTest.fetch(1, pageable)

        then: "repository method was invoked"
        1 * reservedSeatRepository.fetch(1, pageable)
            >> new PageImpl<>([])

        and: "no other interactions"
        0 * _
    }
}