package pl.szudor.room


import org.springframework.dao.EmptyResultDataAccessException
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaRepository
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.film.FilmRepository
import pl.szudor.seating.SeatingRepository
import spock.lang.Specification

class RoomServiceImplTest extends Specification {
    def roomRepository = Mock(RoomRepository)
    def cinemaRepository = Mock(CinemaRepository)
    def filmRepository = Mock(FilmRepository)
    def seatingRepository = Mock(SeatingRepository)
    def underTest = new RoomServiceImpl(roomRepository, cinemaRepository, filmRepository, seatingRepository)

    def "test save room"() {
        given:
        def roomDto = new RoomDto(null, 1, null, null)
        def cinema = new Cinema().tap { it.id = 2 }
        def room = new Room(1, cinema)
        when:
        underTest.saveRoom(roomDto, 2)

        then:
        1 * cinemaRepository.findById(2) >> Optional.of(cinema)
        1 * roomRepository.save(room) >> room

        and:
        0 * _
    }

    def "test save room without found cinema"() {
        given:
        def roomDto = new RoomDto(null, 1, null, null)

        when:
        underTest.saveRoom(roomDto, 2)

        then:
        1 * cinemaRepository.findById(2) >> Optional.empty()
        thrown CinemaNotExistsException

        and:
        0 * _
    }

    def "test update room"() {
        given:
        def id = 2
        def cinema = new Cinema()
        def room = new Room().tap {it.cinema = cinema }

        when:
        underTest.updateRoom(id, new RoomPayload(12))

        then:
        1 * roomRepository.findById(2) >> Optional.of(room)
        1 * roomRepository.save(room) >> room

        and:
        0 * _
    }

    def "test update without found room"() {
        given:
        def id = 2

        when:
        underTest.updateRoom(id, new RoomPayload(12))

        then:
        1 * roomRepository.findById(2) >> Optional.empty()
        thrown RoomNotExistsException

        and:
        0 * _
    }

    def "test delete room"() {
        given:
        def id = 2

        when:
        underTest.deleteRoom(id)

        then:
        1 * roomRepository.deleteById(id)
        1 * seatingRepository.deleteAllByRoomId(id)
        1 * filmRepository.deleteAllByRoomId(id)

        and:
        0 * _
    }

    def "test delete room with wrong room id"() {
        given:
        def id = 2

        when:
        underTest.deleteRoom(id)

        then:
        1 * filmRepository.deleteAllByRoomId(id)
        1 * seatingRepository.deleteAllByRoomId(id)
        1 * roomRepository.deleteById(id) >> { throw new EmptyResultDataAccessException("", 0, new RuntimeException("")) }
        thrown RoomNotExistsException

        and:
        0 * _
    }
}
