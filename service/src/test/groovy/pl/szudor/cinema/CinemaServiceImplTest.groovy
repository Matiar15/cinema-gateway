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
                State.YES)

        when:
        underTest.saveCinema(cinema)

        then:
        1 * cinemaRepository.save(cinema) >> cinema

        and:
        0 * _
    }

    def "get all cinemas"() {
        given:
        def pageable = Mock(Pageable)
        def filter = new CinemaFilter(null, null, null, null, null, null, null, null, null, null,)
        when:
        underTest.getCinemas(pageable, filter)

        then:
        1 * cinemaRepository.fetchByFilter(pageable, filter) >> new PageImpl<Cinema>([])

        and:
        0 * _
    }

    def "update state cinema"() {
        given:
        def cinema = new Cinema().tap {
            it.id = 2
        }

        when:
        underTest.updateState(2, State.NO)

        then:
        1 * cinemaRepository.findById(2) >> Optional.of(cinema)
        1 * cinemaRepository.save(cinema) >> cinema.tap {it.state = State.NO}
    }

    def "test update state cinema with wrong cinema id"() {
        when:
        underTest.updateState(2, State.NO)

        then:
        1 * cinemaRepository.findById(2) >> Optional.empty()
        thrown RuntimeException
    }
}