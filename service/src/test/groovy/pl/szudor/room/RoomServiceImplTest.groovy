package pl.szudor.room

import org.springframework.dao.EmptyResultDataAccessException
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaRepository
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.seat.SeatRepository
import spock.lang.Specification

class RoomServiceImplTest extends Specification {
    RoomRepository roomRepository = Mock()
    CinemaRepository cinemaRepository = Mock()
    RoomFactory roomFactory = Mock()
    SeatRepository seatRepository = Mock()

    def underTest = new RoomServiceImpl(roomRepository, cinemaRepository, roomFactory, seatRepository)

    def entityCinema = new Cinema().tap {
        it.id = 2
    }
    def room = new Room().tap {
        it.number = 1
        it.cinema = entityCinema
    }

    def "save room"() {
        given:
        def savedRoom = new Room().tap {
            it.number = 1
            it.cinema = entityCinema
        }

        when:
        underTest.saveRoom(1, 2)

        then: "look for a cinema"
        1 * cinemaRepository.findById(2) >> Optional.of(entityCinema)

        and: "factory is creating a room"
        1 * roomFactory.createRoom(1, entityCinema) >> room

        and: "room is being saved to the database"
        1 * roomRepository.save(room) >> savedRoom

        and:
        0 * _
    }

    def "save room without found cinema"() {
        when:
        underTest.saveRoom(1, 2)

        then: "look for a cinema gives no result"
        1 * cinemaRepository.findById(2) >> Optional.empty()

        and: "the exception was thrown and no other interactions"
        thrown CinemaNotExistsException
        0 * _
    }

    def "delete room"() {
        when:
        underTest.deleteRoom(1)

        then:
        1 * seatRepository.removeByRoom(1) >> _

        and: "delete a seat by id 1"
        1 * roomRepository.deleteById(1)

        and: "no other interactions"
        0 * _
    }

    def "delete room with wrong room id"() {
        when:
        underTest.deleteRoom(1)

        then:
        1 * seatRepository.removeByRoom(1)

        and: "delete a seat by id 1 throws empty result exceptions"
        1 * roomRepository.deleteById(1) >> { throw new EmptyResultDataAccessException("", 0, new RuntimeException("")) }

        and: "the exception was thrown and no other interactions"
        thrown RoomNotExistsException
        0 * _
    }
}
