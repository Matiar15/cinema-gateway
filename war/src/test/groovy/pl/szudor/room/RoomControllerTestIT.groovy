package pl.szudor.room


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

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/room/populate_with_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RoomControllerTestIT extends Specification {
    @Autowired
    TestRestTemplate testRestTemplate

    @Autowired
    JwtTokenManager tokenManager

    public static final ENDPOINT = "/cinema/1/room"

    def headers = new HttpHeaders()

    def "create room"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def payload = new RoomPayload(1)
        def entity = new HttpEntity(payload, headers)

        when:
        def response = testRestTemplate.exchange(ENDPOINT, HttpMethod.POST, entity, RoomDto.class)

        then: "response is created"
        response.statusCodeValue == 201

        and: "dto was returned"
        response.hasBody()
    }

    def "delete room"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def entity = new HttpEntity(headers)

        when:
        def response = testRestTemplate.exchange("$ENDPOINT/1", HttpMethod.DELETE, entity, Void.class)

        then: "response is no content"
        response.statusCodeValue == 204

        and: "no body returned"
        !response.hasBody()
    }
}
