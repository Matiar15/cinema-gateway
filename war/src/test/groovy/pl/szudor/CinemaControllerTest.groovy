package pl.szudor

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
import pl.szudor.cinema.CinemaController
import pl.szudor.cinema.CinemaDto
import pl.szudor.cinema.CinemaService
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CinemaController.class)
class CinemaControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private CinemaService cinemaService


    private ObjectMapper objectMapper = new ObjectMapper()

    def "test store cinema"() {
        given:
        def cinema = new CinemaDto(
                1,
                "test",
                "test",
                "test",
                "test",
                "test",
                null
        )
        def entity = new Cinema(1,
                "test",
                "test",
                "test",
                "test",
                "test")
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        1 * cinemaService.storeCinema(cinema) >> entity
        result.andExpect(status().is2xxSuccessful())
        and:
        0 * _
    }

    def "test get cinema"() {
        when:
        def result = mvc.perform(get("/cinemas"))

        then:
        1 * cinemaService.getAllCinemas() >> _
        result.andExpect(status().isOk())

        and:
        0 * _
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