package pl.szudor

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.testcontainers.spock.Testcontainers
import pl.szudor.cinema.CinemaDto
import spock.lang.Specification

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CinemaControllerTestIT extends Specification {
    private final String ENDPOINT = "/cinemas"
    private static WireMockServer wireMockServer

    @Autowired
    TestRestTemplate restTemplate

    def setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(12345))
        wireMockServer.start()
    }

    def cleanupSpec() {
        wireMockServer.stop()
    }

    def "test post cinema"() {
        given: "test dto"
        def cinemaDto = new CinemaDto(
                null,
                "test",
                "test",
                "test",
                "test",
                "test",
                null,
                null,
                null,
                null,
                null,
                null
        )

        when:
        def response = restTemplate.postForEntity("$ENDPOINT", cinemaDto, CinemaDto.class)

        then:
        response.hasBody()
        response.getBody() == new CinemaDto(1,
                "test",
                "test",
                "test",
                "test",
                "test",
                null,
                null,
                null,
                null,
                null,
                null)
        response.statusCodeValue == 201
    }
}
