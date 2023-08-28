package pl.szudor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import pl.szudor.repertoire.RepertoireDto
import spock.lang.Specification

import java.time.LocalDate

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:populate_with_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RepertoireControllerTestIT extends Specification {
    private final String ENDPOINT = "/repertoires"

    @Autowired
    TestRestTemplate restTemplate

    def "test post repertoire"() {
        given:
        def repertoireDto = new RepertoireDto(null, LocalDate.of(2019, 3, 3), null, null)

        when:
        def response = restTemplate.postForEntity("$ENDPOINT/1", repertoireDto, RepertoireDto.class)

        then:
        response.statusCodeValue == 201
        response.hasBody()

    }

    def "test get all films"() {
        when:
        def response = restTemplate.getForEntity("$ENDPOINT", RepertoireDto[].class)

        then:
        response.statusCodeValue == 200
        !response.getBody().findAll().empty
    }

    def "test delete film"() {
        when:
        def response = restTemplate.exchange("$ENDPOINT/1", HttpMethod.DELETE, null, String.class)

        then:
        response.statusCodeValue == 204
        response.getBody() == null
    }
}
