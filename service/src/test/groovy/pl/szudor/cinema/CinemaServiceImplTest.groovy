package pl.szudor.cinema


import spock.lang.Specification

import java.time.LocalDate

class CinemaServiceImplTest extends Specification {
    def cinemaRepository = Mock(CinemaRepository)
    def underTest = new CinemaServiceImpl(cinemaRepository)

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
    }

    def "test update state cinema with wrong cinema id"() {
        when:
        underTest.updateState(2, new CinemaPayload(CinemaState.OFF))

        then:
        1 * cinemaRepository.findById(2) >> Optional.empty()
        thrown RuntimeException
    }
}