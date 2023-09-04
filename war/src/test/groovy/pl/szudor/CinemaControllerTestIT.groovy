package pl.szudor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import pl.szudor.cinema.CinemaDto
import pl.szudor.cinema.CinemaState
import spock.lang.Specification
import java.time.LocalDate

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:populate_with_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CinemaControllerTestIT extends Specification {
    private final String ENDPOINT = "/cinemas"

    @Autowired
    TestRestTemplate restTemplate

    def "test post cinema"() {
        given: "test dto"
        def cinemaDto = new CinemaDto(
                null,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                CinemaState.OFF,
                null
        )

        when:
        def response = restTemplate.postForEntity("$ENDPOINT", cinemaDto, CinemaDto.class)

        then:
        response.hasBody()
        response.getBody() == new CinemaDto(
                2,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                CinemaState.OFF,
                response.getBody().createdAt
        )
        response.statusCodeValue == 201
    }

    def "test get cinemas"() {
        when:
        def response = restTemplate.getForEntity("$ENDPOINT", CinemaDto[].class)

        then:
        response.hasBody()
        !response.getBody().findAll().empty
    }

    def "test delete cinema"() {
        when:
        def response = restTemplate.exchange("$ENDPOINT/1", HttpMethod.DELETE, null, String.class)

        then:
        response.statusCodeValue == 204
        response.getBody() == null

    }
}
