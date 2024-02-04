package pl.szudor.film

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import pl.szudor.auth.JwtTokenManager
import pl.szudor.auth.details.UserAuthority
import spock.lang.Specification

import java.time.LocalDate

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/film/populate_with_data.sql")
@Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FilmControllerTestIT extends Specification {
    private final String ENDPOINT = "/film"

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    JwtTokenManager tokenManager

    def headers = new HttpHeaders()

    def "create film"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def payload = new FilmPayload("xD", Pegi.SEVEN, 12, LocalDate.of(2023, 3, 3), "PL_pl")
        def entity = new HttpEntity(payload, headers)

        when:
        def response = restTemplate.exchange(ENDPOINT, HttpMethod.POST, entity, FilmDto.class)

        then: "response status is no content"
        response.statusCodeValue == 201

        and: "there was dto returned"
        response.hasBody()
    }

    def "get all films"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def entity = new HttpEntity(headers)

        when:
        def response = restTemplate.exchange(ENDPOINT, HttpMethod.GET, entity, Map<?, ?>.class)

        then: "get returned film"
        response.hasBody()

        and: "look if film's id was the same as in db record"
        response.getBody()['content']['id'][0] == 1
    }

    def "delete film"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def entity = new HttpEntity(headers)

        when:
        def response = restTemplate.exchange("$ENDPOINT/1", HttpMethod.DELETE, entity, String.class)

        then: "response status is no content"
        response.statusCodeValue == 204
    }
}
