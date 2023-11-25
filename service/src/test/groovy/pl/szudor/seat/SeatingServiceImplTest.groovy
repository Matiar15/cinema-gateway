/*
package pl.szudor.seat


import org.springframework.dao.EmptyResultDataAccessException
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.exception.SeatingNotExistsException
import pl.szudor.room.Room
import pl.szudor.room.RoomRepository
import spock.lang.Specification

class SeatingServiceImplTest extends Specification {
    def seatingRepository = Mock(SeatingRepository)
    def roomRepository = Mock(RoomRepository)
    def underTest = new SeatingServiceImpl(seatingRepository, roomRepository)

    def "save seating"() {
        given:
        def seatingDto = new SeatingDto(null, 1, null, Taken.NO)
        def room = new Room(1, null)
        def seating = new Seating(1, room, Taken.YES)

        when:
        underTest.saveSeating(seatingDto, 2)

        then:
        1 * roomRepository.findById(2) >> Optional.of(room)
        1 * seatingRepository.save(seating) >> seating

        and:
        0 * _
    }

    def "save seating with thrown exception"() {
        given:
        def seatingDto = new SeatingDto(null, 1, null, Taken.NO)

        when:
        underTest.saveSeating(seatingDto, 2)

        then:
        1 * roomRepository.findById(2) >> Optional.empty()
        thrown RoomNotExistsException

        and:
        0 * _
    }

    def "update seating"() {
        given:
        def id = 1
        def room = new Room().tap { it.id = 2 }
        def seatingDto = new SeatingDto(null, 3, null, Taken.NO)
        def seating1 = new Seating(2, room, Taken.NO)
        def seating2 = new Seating(3, room, Taken.YES)

        when:
        underTest.updateSeating(id, seatingDto)

        then:
        1 * seatingRepository.findById(1) >> Optional.of(seating1)
        1 * seatingRepository.save(seating2) >> seating2

        and:
        0 * _
    }

    def "update seating with thrown exception"() {
        given:
        def id = 1
        def seatingDto = new SeatingDto(null, 3, null, Taken.NO)

        when:
        underTest.updateSeating(id, seatingDto)

        then:
        1 * seatingRepository.findById(1) >> Optional.empty()
        thrown SeatingNotExistsException

        and:
        0 * _
    }

    def "delete seating"() {
        given:
        def id = 1

        when:
        underTest.deleteSeating(id)

        then:
        1 * seatingRepository.deleteById(id)

        and:
        0 * _
    }

    def "delete seating with wrong seating id"() {
        given:
        def id = 1

        when:
        underTest.deleteSeating(id)

        then:
        1 * seatingRepository.deleteById(id) >> { throw new EmptyResultDataAccessException("", 0, new RuntimeException("")) }
        thrown SeatingNotExistsException

        and:
        0 * _
    }
}
*/
