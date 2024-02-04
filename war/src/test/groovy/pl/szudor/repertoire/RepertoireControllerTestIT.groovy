package pl.szudor.repertoire

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

import java.time.LocalDate

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/repertoire/populate_with_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RepertoireControllerTestIT extends Specification {
    private final String ENDPOINT = "/cinema"

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    JwtTokenManager tokenManager

    def headers = new HttpHeaders()

    def "post repertoire"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def payload = new RepertoirePayload(LocalDate.of(2098, 3, 3))
        def entity = new HttpEntity(payload, headers)

        when:
        def response = restTemplate.exchange("$ENDPOINT/1/repertoire", HttpMethod.POST, entity, RepertoireDto.class)

        then: "http status created"
        response.statusCodeValue == 201

        and: "response has dto"
        response.hasBody()

    }

    def "post repertoire with data integrity exception"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def payload = new RepertoirePayload(LocalDate.of(2023, 12, 12))
        def entity = new HttpEntity(payload, headers)

        when:
        def response = restTemplate.exchange("$ENDPOINT/1/repertoire", HttpMethod.POST, entity, String.class)

        then: "exception was thrown and status code is 400"
        response.statusCodeValue == 400
    }

    def "get all repertoires"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def entity = new HttpEntity(headers)

        when:
        def response = restTemplate.exchange(ENDPOINT, HttpMethod.GET, entity,  Map<?, ?>.class)

        then: "status is ok"
        response.statusCodeValue == 200

        and: "there was something returned"
        !response.getBody().isEmpty()
    }

    def "patch repertoire"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def entity = new HttpEntity(new RepertoirePayload(LocalDate.of(2099, 3, 3)), headers)

        when:
        def response = restTemplate.exchange("$ENDPOINT/1/repertoire/1", HttpMethod.PATCH, entity, ParameterizedTypeReference.forType(RepertoireDto.class))

        then: "status code is ok"
        response.statusCodeValue == 200

        and: "returned body isn't null"
        response.getBody() != null
    }
}
