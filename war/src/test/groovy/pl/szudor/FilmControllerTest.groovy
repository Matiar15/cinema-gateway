package pl.szudor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import pl.szudor.cinema.CinemaService
import pl.szudor.film.FilmController
import pl.szudor.film.FilmService
import spock.lang.Specification
import spock.mock.DetachedMockFactory

@WebMvcTest(FilmController.class)
class FilmControllerTest extends Specification {
    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    private FilmService filmService

    def ""() {}



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
