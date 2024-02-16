package pl.szudor.film


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.ControllerTestConfig
import pl.szudor.NoSecWebMvcTest
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@NoSecWebMvcTest(FilmController)
@Import(FilmControllerTestConfig)
class FilmControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private FilmService filmService

    private final String ENDPOINT = "/film"

    def time = LocalTime.of(20, 33)
    def tit = "Polska walczaca"
    def peg = Pegi.EIGHTEEN
    def dur = 123
    def relDate = LocalDate.of(2023, 3, 3)
    def orgLang = "PL_pl"
    def dateTime = LocalDateTime.of(relDate, time)

    def savedFilm = new Film().tap {
        it.id = 1
        it.createdAt = dateTime
        it.duration = 123
        it.originalLanguage = "PL_pl"
        it.pegi = peg
        it.title = "Polska walczaca"
        it.releaseDate = relDate
    }

    def "save film all good"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "pegi":             "EIGHTEEN",
        |   "duration":          123,
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "service call was made"
        1 * filmService.saveFilm(tit, peg, dur, relDate, orgLang)
            >> savedFilm

        and: "result was 2xx"
        result.andExpect(status().isCreated())
    }

    def "save film null title"() {
        given:
        def content = """
        |{
        |   "title":            null,
        |   "pegi":             "EIGHTEEN",
        |   "duration":          123,
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be empty")
    }

    def "save film empty title"() {
        given:
        def content = """
        |{
        |   "title":            "",
        |   "pegi":             "EIGHTEEN",
        |   "duration":          123,
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be empty")
    }

    def "save film non existent title"() {
        given:
        def content = """
        |{
        |   "pegi":             "EIGHTEEN",
        |   "duration":          123,
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be empty")
    }

    def "save film null pegi"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "pegi":              null,
        |   "duration":          123,
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "save film non existent pegi"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "duration":          123,
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "save film null duration"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "pegi":             "EIGHTEEN",
        |   "duration":          null,
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "save film not positive duration"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "pegi":             "EIGHTEEN",
        |   "duration":          0,
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must be greater than 0")
    }

    def "save film non existent duration"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "pegi":             "EIGHTEEN",
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "save film null release date"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "pegi":             "EIGHTEEN",
        |   "duration":          123,
        |   "releaseDate":       null,
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "save film non existent release date"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "pegi":             "EIGHTEEN",
        |   "duration":          123,
        |   "originalLanguage": "PL_pl"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "save film null original language"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "pegi":             "EIGHTEEN",
        |   "duration":          123,
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage":  null
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be empty")
    }

    def "save film non existent original language"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "pegi":             "EIGHTEEN",
        |   "duration":          123,
        |   "releaseDate":      "2023-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be empty")
    }

    def "save film empty original language"() {
        given:
        def content = """
        |{
        |   "title":            "Polska walczaca",
        |   "pegi":             "EIGHTEEN",
        |   "duration":          123,
        |   "releaseDate":      "2023-03-03",
        |   "originalLanguage": ""
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be empty")
    }

    def "get films"() {
        given:
        def request = PageRequest.of(0, 5)

        when:
        def result = mvc.perform(get("$ENDPOINT?page=0&size=5"))

        then: "service call was made"
        1 * filmService.fetchByFilter(new FilmFilter(null, null, null, null, null, null, null), request)
            >> new PageImpl<Film>([], request, 0l)

        and: "result was 2xx"
        result.andExpect(status().isOk())
    }

    def "delete film with validated negative path var"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/-1"))

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must be greater than 0")
    }

    def "delete film"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/1"))

        then: "service call was made"
        1 * filmService.deleteFilm(1)

        and: "result was 2xx"
        result.andExpect(status().isNoContent())
    }

    def "delete film negative path variable"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/-1"))

        then: "no service calls were made"
        0 * filmService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must be greater than 0")
    }
}

@Import([ControllerTestConfig])
class FilmControllerTestConfig {
    def detachedMockFactory = new DetachedMockFactory()

    @Bean
    FilmService filmService() {
        return detachedMockFactory.Mock(FilmService.class)
    }
}