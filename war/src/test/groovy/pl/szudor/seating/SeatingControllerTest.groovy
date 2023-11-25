package pl.szudor.seating

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
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.room.Room
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(SeatingController)
class SeatingControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private SeatingService seatingService

    private final String ENDPOINT = "/seat"

    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        objectMapper.findAndRegisterModules()
    }

    def "create seat"() {
        given:
        def seatingDto = new SeatingDto(null, 12, null, Taken.NO)
        def postContent = objectMapper.writeValueAsString(seatingDto)
        when:
        def result = mvc.perform(post("$ENDPOINT/room/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(postContent)
        )

        then:
        1 * seatingService.saveSeating(seatingDto, 1)
                >> new Seating(12, new Room().tap { it.id = 1 }, Taken.YES)

        result.andExpect(status().is2xxSuccessful())
    }

    def "create seat with thrown exception"() {
        given:
        def seatingDto = new SeatingDto(null, 12, null, Taken.NO)
        def postContent = objectMapper.writeValueAsString(seatingDto)
        when:
        def result = mvc.perform(post("$ENDPOINT/room/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(postContent)
        )

        then:
        1 * seatingService.saveSeating(seatingDto, 1)
                >> { throw new RoomNotExistsException(1) }

        result.andExpect(status().is4xxClientError())
    }

    def "update seating"() {
        given:
        def seatingDto = new SeatingDto(null, 13, null, Taken.NO)
        def updateContent = objectMapper.writeValueAsString(seatingDto)
        def room = new Room().tap {
            it.id = 1
        }
        def seating = new Seating(13, room, Taken.NO).tap {
            it.id = 1
        }
        when:
        def result = mvc.perform(put("$ENDPOINT/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent)
        )

        then:
        1 * seatingService.updateSeating(1, seatingDto)
                >> seating

        result.andExpect(status().is2xxSuccessful())
    }

    def "delete seating"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/1"))

        then:
        1 * seatingService.deleteSeating(1)
        result.andExpect(status().is2xxSuccessful())
    }

    @TestConfiguration
    @Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
    static class CinemaControllerTestConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        SeatingService seatingService() {
            return detachedMockFactory.Mock(SeatingService.class)
        }
    }
}
