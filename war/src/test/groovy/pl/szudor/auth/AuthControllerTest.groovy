package pl.szudor.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.ControllerTestConfig
import pl.szudor.NoSecWebMvcTest
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static pl.szudor.auth.AuthControllerTest.TestData.*
import static pl.szudor.auth.AuthControllerTest.TestData.badEmail

@NoSecWebMvcTest(AuthController)
@Import(AuthControllerTestConfig)
class AuthControllerTest extends Specification {
    @Autowired
    MockMvc mvc

    @Autowired
    CredentialAuthorizationService credentialAuthorizationService

    @Autowired
    CustomUserDetailsService customUserDetailsService

    private static final String ENDPOINT = "/auth"

    def "createToken should validate all good"() {
        given:
        def content = """
        |{
        |   "username": "$username",
        |   "password": "$password"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT + "/token")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        1 * credentialAuthorizationService.generateToken(username, password) >> token

        and:
        result.andExpect(status().isCreated())

        and:
        result.andReturn().response.contentAsString.contains(token)
    }

    def "create should validate all good"() {
        given:
        def content = """
        |{
        |   "username": "$username",
        |   "password": "$password",
        |   "email":    "$email"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        1 * customUserDetailsService.createUser(username, password, email)

        and:
        result.andExpect(status().isCreated())
    }

    def "patch should validate all good"() {
        given:
        def content = """
        |{
        |   "email": "$email"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch(ENDPOINT + "/" + username)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        1 * customUserDetailsService.addUserEmail(username, email)

        and:
        result.andExpect(status().isNoContent())
    }

    def "createToken should validate null username"() {
        given:
        def content = """
        |{
        |   "username": null,
        |   "password": "$password"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT + "/token")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * credentialAuthorizationService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "createToken should validate username size"() {
        given:
        def content = """
        |{
        |   "username": "$badUsername",
        |   "password": "$password"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT + "/token")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * credentialAuthorizationService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("size must be between 5 and 20")
    }

    def "createToken should validate null password"() {
        given:
        def content = """
        |{
        |   "username": "$username",
        |   "password": null
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT + "/token")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * credentialAuthorizationService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "createToken should validate password size"() {
        given:
        def content = """
        |{
        |   "username": "$username",
        |   "password": "$badPassword"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT + "/token")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * credentialAuthorizationService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("size must be between 5 and 20")
    }

    def "create should validate null username"() {
        given:
        def content = """
        |{
        |   "username": null,
        |   "password": "$password",
        |   "email":    "$email"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * customUserDetailsService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "create should validate username size"() {
        given:
        def content = """
        |{
        |   "username": "$badUsername",
        |   "password": "$password",
        |   "email":    "$email"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * customUserDetailsService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("size must be between 5 and 20")
    }

    def "create should validate null password"() {
        given:
        def content = """
        |{
        |   "username": "$username",
        |   "password": null,
        |   "email":    "$email"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * customUserDetailsService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "create should validate password size"() {
        given:
        def content = """
        |{
        |   "username": "$username",
        |   "password": "$badPassword",
        |   "email":    "$email"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * customUserDetailsService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("size must be between 5 and 20")
    }

    def "create should validate wrong email"() {
        given:
        def content = """
        |{
        |   "username": "$username",
        |   "password": "$password",
        |   "email":    "$badEmail"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * customUserDetailsService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("must be a well-formed email address")
    }

    def "patch should validate wrong email"() {
        given:
        def content = """
        |{
        |   "email": "$badEmail"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch(ENDPOINT + "/" + username)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * customUserDetailsService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("must be a well-formed email address")
    }

    def "patch should validate blank string in url"() {
        given:
        def content = """
        |{
        |   "email": "$email"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch(ENDPOINT + "/" + " ")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * customUserDetailsService._

        and:
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("must not be blank")
    }

    class TestData {
        static username = "Matiar"
        static badUsername = "Mat"
        static password = "Pword"
        static badPassword = "Peda"
        static token = "TOKEN"
        static email = "matpat@google.com"
        static badEmail = "ww@ww@.pl"
    }
}

@Import([ControllerTestConfig])
class AuthControllerTestConfig {
    def detachedMockFactory = new DetachedMockFactory()

    @Bean
    CredentialAuthorizationService credentialAuthorizationService() {
        return detachedMockFactory.Mock(CredentialAuthorizationService.class)
    }

    @Bean
    CustomUserDetailsService customUserDetailsService() {
        return detachedMockFactory.Mock(CustomUserDetailsService.class)
    }
}