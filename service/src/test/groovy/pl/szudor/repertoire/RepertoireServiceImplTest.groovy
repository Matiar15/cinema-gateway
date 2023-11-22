package pl.szudor.repertoire

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.Active
import pl.szudor.film.FilmRepository
import spock.lang.Specification

import java.time.LocalDate

class RepertoireServiceImplTest extends Specification {
    def cinemaRepository = Mock(CinemaRepository)
    def repertoireRepository = Mock(RepertoireRepository)
    def filmRepository = Mock(FilmRepository)
    def underTest = new RepertoireServiceImpl(repertoireRepository, cinemaRepository, filmRepository)

    def "save repertoire all good"() {
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
                LocalDate.of(2019, 3, 22),
                Active.NO
        )
        def repertoireWhenPlayed = LocalDate.of(2022, 12, 23)
        def repertoireDto = new RepertoireDto(1,
                repertoireWhenPlayed,
                null,
                null
        )
        def repertoire = new Repertoire(1, repertoireWhenPlayed, cinema)
        when:
        underTest.saveRepertoire(repertoireDto, 2)
        then:
        1 * cinemaRepository.findById(2) >> Optional.of(cinema)
        1 * repertoireRepository.save(repertoire) >> repertoire

        and:
        0 * _
    }

    def "save repertoire without found cinema"() {
        given:
        def repertoireWhenPlayed = LocalDate.of(2022, 12, 23)
        def repertoireDto = new RepertoireDto(1,
                repertoireWhenPlayed,
                null,
                null
        )

        when:
        underTest.saveRepertoire(repertoireDto, 2)

        then:
        1 * cinemaRepository.findById(2) >> Optional.empty()
        thrown RuntimeException

        and:
        0 * _
    }

    def "get repertoires without any repertoires"() {
        given:
        def pageable = Mock(Pageable)

        when:
        underTest.getAll(pageable)

        then:
        1 * repertoireRepository.findAllRepertoires(pageable) >> new PageImpl<RepertoireDto>([])

        and:
        0 * _
    }

    def "delete repertoire"() {
        when:
        underTest.deleteRepertoire(2)

        then:
        1 * filmRepository.deleteAllByRepertoireId(2)
        1 * repertoireRepository.deleteById(2)
    }

    def "delete repertoire with wrong repertoire id"() {
        when:
        underTest.deleteRepertoire(2)

        then:
        1 * filmRepository.deleteAllByRepertoireId(2)
        1 * repertoireRepository.deleteById(2) >> { throw new EmptyResultDataAccessException("", 0, new RuntimeException("")) }
        thrown RuntimeException
    }
}
