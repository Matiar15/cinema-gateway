package pl.szudor.room

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
import pl.szudor.cinema.State
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.exception.RoomNotExistsException
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RoomController)
class RoomControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private RoomService roomService

    private final String CORRECT_CINEMA_ID = 1
    private final String WRONG_CINEMA_ID = -1
    private final String CORRECT_URL = "/cinema/$CORRECT_CINEMA_ID/room"
    private final String WRONG_URL = "/cinema/$WRONG_CINEMA_ID/room"

    def entityCinema = new Cinema().tap {
        it.id = 1
        it.name = "test"
        it.address = "test"
        it.email = "xdddd@wp.pl"
        it.phoneNumber = "+48-123-456-789"
        it.postalCode = "99-999"
        it.director = "test"
        it.nipCode = "1234567890"
        it.buildDate = LocalDate.of(2023, 3, 3)
        it.state = State.NO
    }

    def room = new Room().tap {
        it.id = 1
        it.number = 12
        it.cinema = entityCinema
    }

    def "create room should validate all good"() {
        given:
        def content = """
        |{
        |   "number": 1
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$CORRECT_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        1 * roomService.saveRoom(1, 1) >> room

        and:
        result.andExpect(status().is2xxSuccessful())
    }

    def "create room should validate null number"() {
        given:
        def content = """
        |{
        |   "number": null
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$CORRECT_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * roomService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "create room should validate negative number"() {
        given:
        def content = """
        |{
        |   "number": -1
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$CORRECT_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * roomService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "create room should validate null body"() {
        when:
        def result = mvc.perform(post("$CORRECT_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        then:
        0 * roomService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "create room should validate null body"() {
        when:
        def result = mvc.perform(post("$CORRECT_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        then:
        0 * roomService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "create room should validate negative cinema id"() {
        given:
        def content = """
        |{
        |   "number": 1
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$WRONG_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * roomService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "delete room should validate all good"() {
        when:
        def result = mvc.perform(delete("$CORRECT_URL/12"))

        then:
        1 * roomService.deleteRoom(12)

        and:
        result.andExpect(status().is2xxSuccessful())
    }

    def "delete room should validate negative cinema id"() {
        when:
        def result = mvc.perform(delete("$WRONG_URL/12"))

        then:
        0 * roomService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "delete room should validate negative room id"() {
        when:
        def result = mvc.perform(delete("$WRONG_URL/12"))

        then:
        0 * roomService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "delete room should validate cinema not found"() {
        when:
        def result = mvc.perform(delete("$CORRECT_URL/12"))

        then:
        1 * roomService.deleteRoom(12)
            >> { throw new CinemaNotExistsException(1) }

        and:
        result.andExpect(status().isNotFound())
    }

    def "delete room should validate room not found"() {
        when:
        def result = mvc.perform(delete("$CORRECT_URL/12"))

        then:
        1 * roomService.deleteRoom(12)
                >> { throw new RoomNotExistsException(12) }

        and:
        result.andExpect(status().isNotFound())
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
