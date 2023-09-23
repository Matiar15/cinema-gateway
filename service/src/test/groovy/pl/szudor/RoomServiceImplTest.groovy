package pl.szudor

import org.slf4j.Logger
import pl.szudor.room.Room
import pl.szudor.room.RoomDto
import pl.szudor.room.RoomRepository
import pl.szudor.room.RoomServiceImpl
import spock.lang.Specification

class RoomServiceImplTest extends Specification {
    def roomRepository = Mock(RoomRepository)
    def underTest = new RoomServiceImpl(roomRepository)
    def logger = underTest.logger = Mock(Logger)

    def "test save cinema"() {
        given:
        def roomDto = new RoomDto(null, 1, null, null)
        def room = new Room(1, null)
        when:
        underTest.saveRoom(roomDto)

        then:
        1 * logger.info(_)
        1 * roomRepository.save(room) >> room

        and:
        0 * _
    }
}
