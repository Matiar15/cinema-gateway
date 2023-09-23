package pl.szudor

import org.slf4j.Logger
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaDto
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.CinemaServiceImpl
import pl.szudor.cinema.CinemaState
import pl.szudor.repertoire.RepertoireRepository
import spock.lang.Specification

import java.time.LocalDate

class CinemaServiceImplTest extends Specification {
    def cinemaRepository = Mock(CinemaRepository)
    def repertoireRepository = Mock(RepertoireRepository)
    def underTest = new CinemaServiceImpl(cinemaRepository, repertoireRepository)
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

    def "test delete cinema"() {
        when:
        underTest.deleteCinema(2)

        then:
        1 * repertoireRepository.deleteAllByCinemaId(2)
        1 * cinemaRepository.deleteById(2)
        2 * logger.info(_)
    }

    def "test delete cinema with wrong cinema id"() {
        when:
        underTest.deleteCinema(2)

        then:
        1 * repertoireRepository.deleteAllByCinemaId(2)
        1 * cinemaRepository.deleteById(2) >> Optional.empty()
        thrown RuntimeException
        2 * logger.info(_)
    }
}