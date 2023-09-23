package pl.szudor

import org.slf4j.Logger
import pl.szudor.cinema.Cinema
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.room.Room
import pl.szudor.room.RoomRepository
import pl.szudor.seating.Seating
import pl.szudor.seating.SeatingDto
import pl.szudor.seating.SeatingRepository
import pl.szudor.seating.SeatingServiceImpl
import spock.lang.Specification

class SeatingServiceImplTest extends Specification {
    def seatingRepository = Mock(SeatingRepository)
    def roomRepository = Mock(RoomRepository)
    def underTest = new SeatingServiceImpl(seatingRepository, roomRepository)
    def logger = underTest.logger = Mock(Logger)

    def "test save seating"() {
        given:
        def seatingDto = new SeatingDto(null, 1, null)

        def room = new Room(1, null)
        def seating = new Seating(1, room)

        when:
        underTest.saveSeating(seatingDto, 2)

        then:
        1 * logger.info(_)
        1 * roomRepository.findById(2) >> Optional.of(room)
        1 * seatingRepository.save(seating) >> seating

        and:
        0 * _
    }

    def "test save seating with thrown exception"() {
        given:
        def seatingDto = new SeatingDto(null, 1, null)

        when:
        underTest.saveSeating(seatingDto, 2)

        then:
        1 * logger.info(_)
        1 * roomRepository.findById(2) >> Optional.empty()
        thrown RoomNotExistsException

        and:
        0 * _
    }
}
