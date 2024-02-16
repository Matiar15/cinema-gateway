package pl.szudor.event

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import pl.szudor.auth.JwtTokenManager
import pl.szudor.auth.details.UserAuthority
import spock.lang.Specification

import java.time.LocalTime

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/event/populate_with_data.sql")
@Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EventControllerTestIT extends Specification {
    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    JwtTokenManager tokenManager

    def headers = new HttpHeaders()

    def "post event"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def payload = new EventPayload(LocalTime.of(23, 23))
        def entity = new HttpEntity(payload, headers)

        when:
        def response = restTemplate.exchange("/repertoire/1/film/1/room/1/event", HttpMethod.POST, entity, EventDto.class)

        then: "dto was returned"
        response.hasBody()

        and: "status is created"
        response.getStatusCodeValue() == 201
    }

    def "get all events"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def entity = new HttpEntity(headers)

        when:
        def response = restTemplate.exchange("/repertoire/1/film/1/room/1/event", HttpMethod.GET, entity, Map<?, ?>.class)

        then: "get returned event"
        response.hasBody()
    }

    def "patch event"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def entity = new HttpEntity(new EventPayload(LocalTime.of(9, 12)), headers)

        when:
        def response = restTemplate.exchange("/repertoire/1/film/1/room/1/event/1", HttpMethod.PATCH, entity, ParameterizedTypeReference.forType(EventDto.class))

        then: "response status is no content"
        response.statusCodeValue == 200
    }

    def "delete event"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def entity = new HttpEntity(headers)

        when:
        def response = restTemplate.exchange("/repertoire/1/film/1/room/1/event/1", HttpMethod.DELETE, entity, String.class)

        then: "response status is no content"
        response.statusCodeValue == 204
    }
}