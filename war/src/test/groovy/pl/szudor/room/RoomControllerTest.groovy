/*
package pl.szudor.room

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.cinema.Cinema
import pl.szudor.exception.CinemaNotExistsException
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RoomController)
class RoomControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private RoomService roomService

    private final String ENDPOINT = "/room"

    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        objectMapper.findAndRegisterModules()
    }

    def "store room"() {
        given:
        def roomDto = new RoomDto(null, 12, null, null)
        def postContent = objectMapper.writeValueAsString(roomDto)
        def cinema = new Cinema()
        def room = new Room().tap {
            it.id = 1
            it.roomNumber = 12
            it.cinema = cinema
        }
        when:
        def result = mvc.perform(post("$ENDPOINT/cinema/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(postContent))

        then:
        result.andExpect(status().is2xxSuccessful())
        1 * roomService.saveRoom(roomDto, 1) >> room

        and:
        0 * _

    }

    def "store room with thrown exception"() {
        given:
        def roomDto = new RoomDto(null, 12, null, null)
        def postContent = objectMapper.writeValueAsString(roomDto)

        when:
        def result = mvc.perform(post("$ENDPOINT/cinema/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(postContent))

        then:
        result.andExpect(status().is4xxClientError())
        1 * roomService.saveRoom(roomDto, 1) >> { throw new CinemaNotExistsException(1) }

        and:
        0 * _
    }

    def "update room"() {
        given:
        def roomPayload = new RoomPayload(12)
        def updateContent = objectMapper.writeValueAsString(roomPayload)

        when:
        def result = mvc.perform(put("$ENDPOINT/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent)
        )

        then:
        result.andExpect(status().is2xxSuccessful())
        1 * roomService.updateRoom(1, roomPayload) >> new Room(12, null)
    }

    def "delete room"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/12"))

        then:
        result.andExpect(status().is2xxSuccessful())
        1 * roomService.deleteRoom(12)
    }

    @TestConfiguration
    @Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
    static class CinemaControllerTestConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        RoomService roomService() {
            return detachedMockFactory.Mock(RoomService.class)
        }
    }
}
*/
