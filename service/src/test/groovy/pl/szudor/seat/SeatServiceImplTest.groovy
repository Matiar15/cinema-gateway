package pl.szudor.seat

import org.springframework.dao.EmptyResultDataAccessException
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.exception.SeatingNotExistsException
import pl.szudor.room.Room
import pl.szudor.room.RoomRepository
import spock.lang.Specification

class SeatServiceImplTest extends Specification {
    SeatRepository seatRepository = Mock()
    RoomRepository roomRepository = Mock()
    SeatFactory seatFactory = Mock()
    def underTest = new SeatServiceImpl(seatRepository, roomRepository, seatFactory)

    def room = new Room(1, 12, null)
    def seat = new Seat(0, 2, Occupied.NO, room)
    def createdSeat = new Seat(1, 2, Occupied.NO, room)

    def "save seat"() {
        when:
        underTest.saveSeat(1, 2)

        then: "look for a room"
        1 * roomRepository.findById(1) >> Optional.of(room)

        and: "factory is creating a seat"
        1 * seatFactory.createSeat(2, Occupied.NO, room) >> seat

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

    def "update seating"() {
        given:
        def patchedSeat = new Seat(0, 2, Occupied.YES, room)
        def patchedCreatedSeat = new Seat(0, 2, Occupied.YES, room)

        when:
        underTest.patchSeat(1, Occupied.YES)

        then: "look for a seat"
        1 * seatRepository.findById(1) >> Optional.of(seat)

        and: "save patched seat"
        1 * seatRepository.save(patchedSeat) >> patchedCreatedSeat

        and: "no other interactions"
        0 * _
    }

    def "update seating with thrown exception"() {
        when:
        underTest.patchSeat(1, Occupied.YES)

        then: "look for a seat"
        1 * seatRepository.findById(1) >> Optional.empty()

        and: "the exception was thrown and no other interactions"
        thrown SeatingNotExistsException
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
        thrown SeatingNotExistsException
        0 * _
    }
}
