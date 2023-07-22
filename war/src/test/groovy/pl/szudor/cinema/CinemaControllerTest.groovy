package pl.szudor.cinema

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.CinemaApp
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content


@WebMvcTest(controllers = CinemaController.class)
class CinemaControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private CinemaService cinemaService

    private ObjectMapper objectMapper = new ObjectMapper()

    def "test store cinema"() {
        given:
        def cinema = new CinemaDto(
                "test",
                "test",
                "test",
                "test",
                "test"
        )

        def cinemaAsJson = objectMapper.writeValueAsString(cinema)
        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))
        then:
        1 * cinemaService.storeCinema(cinema)
        result.andExpect(status().is2xxSuccessful())

        and:
        0 * _
    }

    def "test get cinema"() {
        when:
        def result = mvc.perform(get("/cinemas"))

        then:
        1 * cinemaService.getAllCinemas()
        result.andExpect(status().isOk())

        and:
        0 * _
    }

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        CinemaService cinemaService() {
            return detachedMockFactory.Mock(CinemaService)
        }
    }
}