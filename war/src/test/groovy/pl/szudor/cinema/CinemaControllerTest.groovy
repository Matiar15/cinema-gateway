package pl.szudor.cinema

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.exception.CinemaNotExistsException
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CinemaController.class)
class CinemaControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private CinemaService cinemaService


    private ObjectMapper objectMapper = new ObjectMapper()


    def setup() {
        objectMapper.findAndRegisterModules()
    }

    def "create cinema all good"() {
        given:
        def cinema = new CinemaPayload(
                1,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )
        def entity = new Cinema(1,
                "test",
                "test",
                "xdddd@wp.pl",
                "123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO)
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        1 * cinemaService.saveCinema(entity) >> entity
        result.andExpect(status().is2xxSuccessful())

        and:
        0 * _
    }

    def "create cinema null name"() {
        given:
        def cinema = new CinemaPayload(
                null,
                null,
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null address"() {
        given:
        def cinema = new CinemaPayload(
                null,
                "name",
                null,
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null email"() {
        given:
        def cinema = new CinemaPayload(
                1,
                "test",
                "test",
                null,
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null phone number"() {
        given:
        def cinema = new CinemaPayload(
                1,
                "test",
                "test",
                "xdddd@wp.pl",
                null,
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null postal code"() {
        given:
        def cinema = new CinemaPayload(
                1,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                null,
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null director"() {
        given:
        def cinema = new CinemaPayload(
                1,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                null,
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null nip code"() {
        given:
        def cinema = new CinemaPayload(
                1,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                null,
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null nip code"() {
        given:
        def cinema = new CinemaPayload(
                1,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                null,
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null created date"() {
        given:
        def cinema = new CinemaPayload(
                1,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                null,
                State.NO,
                null
        )
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null state"() {
        given:
        def cinema = new CinemaPayload(
                1,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                null,
                null
        )
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }


    def "create cinema all good"() {
        given:
        def cinema = new CinemaPayload(
                1,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )
        def entity = new Cinema(1,
                "test",
                "test",
                "xdddd@wp.pl",
                "123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO)
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        1 * cinemaService.saveCinema(entity) >> entity
        result.andExpect(status().is2xxSuccessful())

        and:
        0 * _
    }

    def "get cinemas"() {
        when:
        def result = mvc.perform(get("/cinemas?page=0&size=5"))

        then:
        1 * cinemaService.getCinemas(PageRequest.of(0, 5), new CinemaFilter(null, null, null, null, null, null, null, null, null, null))
                >> _
        result.andExpect(status().isOk())

        and:
        0 * _
    }

    def "patch cinema null state"() {
        given:
        def payload = new CinemaPatchPayload(null)
        def updateContent = objectMapper.writeValueAsString(payload)

        when:
        def result = mvc.perform(patch("/cinemas/22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent)
        )

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())
    }

    def "patch cinema negative id"() {
        given:
        def payload = new CinemaPatchPayload(State.NO)
        def updateContent = objectMapper.writeValueAsString(payload)

        when:
        def result = mvc.perform(patch("/cinemas/-2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent)
        )

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())
    }

    def "patch cinema"() {
        given:
        def payload = new CinemaPatchPayload(State.NO)
        def updateContent = objectMapper.writeValueAsString(payload)
        def entity = new Cinema(1,
                "test",
                "test",
                "xdddd@wp.pl",
                "123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO
        )

        when:
        def result = mvc.perform(patch("/cinemas/22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent)
        )

        then:
        1 * cinemaService.updateState(22, payload.state) >> entity
        result.andExpect(status().is2xxSuccessful())
    }

    def "patch cinema without found cinema"() {
        given:
        def payload = new CinemaPatchPayload(State.NO)
        def updateContent = objectMapper.writeValueAsString(payload)


        when:
        def result = mvc.perform(patch("/cinemas/22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent)
        )

        then:
        1 * cinemaService.updateState(22, payload.state) >> { throw new CinemaNotExistsException(22) }
        result.andExpect(status().is4xxClientError())
    }

    @TestConfiguration
    @Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
    static class CinemaControllerTestConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        CinemaService cinemaService() {
            return detachedMockFactory.Mock(CinemaService.class)
        }

    }
}