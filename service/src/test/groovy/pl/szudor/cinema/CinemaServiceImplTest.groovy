package pl.szudor.cinema

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDate

class CinemaServiceImplTest extends Specification {
    def cinemaRepository = Mock(CinemaRepository)
    def underTest = new CinemaServiceImpl(cinemaRepository)

    def "save cinema"() {
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

    def "get all cinemas"() {
        given:
        def pageable = Mock(Pageable)

        when:
        underTest.getCinemas(pageable)

        then:
        1 * cinemaRepository.findAllCinemas(pageable) >> new PageImpl<CinemaDto>([])

        and:
        0 * _
    }

    def "update state cinema"() {
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