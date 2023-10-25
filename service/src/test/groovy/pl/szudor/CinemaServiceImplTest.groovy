package pl.szudor

import org.slf4j.Logger
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaDto
import pl.szudor.cinema.CinemaPayload
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.CinemaServiceImpl
import pl.szudor.cinema.CinemaState
import pl.szudor.repertoire.RepertoireRepository
import spock.lang.Specification

import java.time.LocalDate

class CinemaServiceImplTest extends Specification {
    def cinemaRepository = Mock(CinemaRepository)
    def repertoireRepository = Mock(RepertoireRepository)
    def underTest = new CinemaServiceImpl(cinemaRepository)
    def logger = underTest.logger = Mock(Logger)

    def "test save cinema"() {
        given:
        def cinemaDto = new CinemaDto(
                1,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                LocalDate.of(2019, 3, 30),
                CinemaState.ON,
                null)
        def cinema = new Cinema(
                1,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                LocalDate.of(2019, 3, 30),
                CinemaState.ON)

        when:
        underTest.saveCinema(cinemaDto)

        then:
        1 * cinemaRepository.save(cinema) >> cinema
        1 * logger.info(_)

        and:
        0 * _
    }

    def "test get all cinemas"() {
        when:
        underTest.getCinemas()

        then:
        1 * cinemaRepository.findAll() >> _

        and:
        0 * _
    }

    def "test update state cinema"() {
        given:
        def cinema = new Cinema().tap {
            it.id = 2
        }

        when:
        underTest.updateState(2, new CinemaPayload(CinemaState.OFF))

        then:
        1 * cinemaRepository.findById(2) >> Optional.of(cinema)
        1 * cinemaRepository.save(cinema) >> cinema.tap {it.currentState = CinemaState.OFF}
        1 * logger.info(_)
    }

    def "test update state cinema with wrong cinema id"() {
        when:
        underTest.updateState(2, new CinemaPayload(CinemaState.OFF))

        then:
        1 * cinemaRepository.findById(2) >> Optional.empty()
        thrown RuntimeException
        1 * logger.info(_)
    }
}