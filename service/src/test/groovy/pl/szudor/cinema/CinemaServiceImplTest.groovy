package pl.szudor.cinema

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import pl.szudor.exception.CinemaNotExistsException
import spock.lang.Specification

import java.time.LocalDate

class CinemaServiceImplTest extends Specification {
    CinemaRepository cinemaRepository = Mock()
    CinemaFactory cinemaFactory = Mock()
    def underTest = new CinemaServiceImpl(cinemaRepository, cinemaFactory)

    def "save cinema"() {
        given:
        def cinema = new Cinema().tap {
            it.name = ""
            it.address = ""
            it.active = Active.YES
            it.buildDate = LocalDate.of(2019, 3, 30)
            it.director = ""
            it.nipCode = ""
            it.postalCode = ""
            it.phoneNumber = ""
            it.email = ""
        }
        def savedCinema = new Cinema().tap {
            it.id = 1
            it.name = ""
            it.address = ""
            it.active = Active.YES
            it.buildDate = LocalDate.of(2019, 3, 30)
            it.director = ""
            it.nipCode = ""
            it.postalCode = ""
            it.phoneNumber = ""
            it.email = ""
        }

        when:
        underTest.saveCinema("",
                "",
                "",
                "",
                "",
                "",
                "",
                LocalDate.of(2019, 3, 30)
        )

        then:
        1 * cinemaFactory.createCinema("",
                "",
                "",
                "",
                "",
                "",
                "",
                LocalDate.of(2019, 3, 30)
        ) >> cinema

        and:
        1 * cinemaRepository.save(cinema) >> savedCinema

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
        underTest.updateState(2, Active.NO)

        then:
        1 * cinemaRepository.findById(2) >> Optional.of(cinema)

        and:
        1 * cinemaRepository.save(cinema) >> cinema.tap {it.active = Active.NO}

        and:
        0 * _
    }

    def "test update state cinema with wrong cinema id"() {
        when:
        underTest.updateState(2, Active.NO)

        then:
        1 * cinemaRepository.findById(2) >> Optional.empty()

        and:
        thrown CinemaNotExistsException
        0 * _
    }
}