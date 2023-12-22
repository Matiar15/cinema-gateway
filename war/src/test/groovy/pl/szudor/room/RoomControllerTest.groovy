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
import pl.szudor.cinema.Active
import pl.szudor.cinema.Cinema
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RoomController.class)
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
        it.active = Active.NO
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

        then: "service calls were made"
        1 * roomService.saveRoom(1, 1) >> room

        and: "result was 2xx"
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

        then: "no service calls were made"
        0 * roomService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
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

        then: "no service calls were made"
        0 * roomService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must be greater than 0")
    }

    def "create room should validate null body"() {
        when:
        def result = mvc.perform(post("$CORRECT_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        then: "no service calls were made"
        0 * roomService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("request body is missing")
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

        then: "no service calls were made"
        0 * roomService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must be greater than 0")
    }

    def "delete room should validate all good"() {
        when:
        def result = mvc.perform(delete("$CORRECT_URL/12"))

        then: "service calls were made"
        1 * roomService.deleteRoom(12)

        and: "result was 2xx"
        result.andExpect(status().is2xxSuccessful())
    }

    def "delete room should validate negative cinema id"() {
        when:
        def result = mvc.perform(delete("$WRONG_URL/12"))

        then: "no service calls were made"
        0 * roomService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must be greater than 0")
    }

    def "delete room should validate negative room id"() {
        when:
        def result = mvc.perform(delete("$WRONG_URL/12"))

        then: "no service calls were made"
        0 * roomService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must be greater than 0")
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
