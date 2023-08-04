package pl.szudor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaDto
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CinemaControllerTestIT extends Specification {
    /*private final String ENDPOINT = "/cinema"

    @Autowired
    TestRestTemplate restTemplate

    @Shared
    PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("foo")
            .withUsername("foo")
            .withPassword("secret")
            .
    def setup() {
        postgreSQLContainer.start()
    }

    def cleanupSpec() {

    }

    def "test post cinema"() {
        given: "test dto"
        def cinemaDto = new CinemaDto(
                null,
                "test",
                "test",
                "test",
                "test",
                "test"
        )

        when:
        def response = restTemplate.postForEntity("$ENDPOINT", cinemaDto, Cinema.class)

        then:
        response.hasBody()
        response.getBody() == []
        response.statusCodeValue == 204
    }*/
}
