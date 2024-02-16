package pl.szudor.seat

import org.springframework.dao.EmptyResultDataAccessException
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.exception.SeatNotExistsException
import pl.szudor.room.Room
import pl.szudor.room.RoomRepository
import spock.lang.Specification

class SeatServiceImplTest extends Specification {
    SeatRepository seatRepository = Mock()
    RoomRepository roomRepository = Mock()
    SeatFactory seatFactory = Mock()
    def underTest = new SeatServiceImpl(seatRepository, roomRepository, seatFactory)

    def entityRoom = new Room().tap {
        it.id = 1
        it.number = 12
    }
    def seat = new Seat().tap {
        it.room = entityRoom
        it.number = 2
    }
    def createdSeat = new Seat().tap {
        it.id = 1
        it.room = entityRoom
        it.number = 2
    }

    def "save seat"() {
        when:
        underTest.saveSeat(1, 2)

        then: "look for a room"
        1 * roomRepository.findById(1) >> Optional.of(entityRoom)

        and: "factory is creating a seat"
        1 * seatFactory.createSeat(2, entityRoom) >> seat

        and: "seat is being saved to the database"
        1 * seatRepository.save(seat) >> createdSeat

        and: "no other interactions"
        0 * _
    }

    def "save seating with thrown exception"() {
        when:
        underTest.saveSeat(1, 2)

        then: "look for a room"
        1 * roomRepository.findById(1) >> Optional.empty()

        and: "the exception was thrown and no other interactions"
        thrown RoomNotExistsException
        0 * _
    }

    def "delete seating"() {
        when:
        underTest.deleteSeat(1)

        then: "delete a seat by id 1"
        1 * seatRepository.deleteById(1)

        and: "no other interactions"
        0 * _
    }

    def "delete seating with wrong seating id"() {
        when:
        underTest.deleteSeat(1)

        then: "delete a seat by id 1 throws empty result exceptions"
        1 * seatRepository.deleteById(1)
                >> { throw new EmptyResultDataAccessException("", 0, new RuntimeException("")) }

        and: "the exception was thrown and no other interactions"
        thrown SeatNotExistsException
        0 * _
    }
}
