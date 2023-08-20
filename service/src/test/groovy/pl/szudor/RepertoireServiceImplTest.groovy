package pl.szudor


import org.slf4j.Logger
import org.springframework.dao.EmptyResultDataAccessException
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaDto
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.CinemaState
import pl.szudor.film.FilmRepository
import pl.szudor.repertoire.Repertoire
import pl.szudor.repertoire.RepertoireDto
import pl.szudor.repertoire.RepertoireRepository
import pl.szudor.repertoire.RepertoireServiceImpl
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class RepertoireServiceImplTest extends Specification {
    def cinemaRepository = Mock(CinemaRepository)
    def repertoireRepository = Mock(RepertoireRepository)
    def filmRepository = Mock(FilmRepository)
    def underTest = new RepertoireServiceImpl(repertoireRepository, cinemaRepository, filmRepository)
    def logger = underTest.logger = Mock(Logger)

    def "test save repertoire"() {
        given:
        def cinema = new Cinema(
                2,
                "",
                "",
                "",
                "",
                "",
                "",
                LocalDate.of(2019, 3, 22),
                CinemaState.OFF)
        def cinemaDto = new CinemaDto(
                2,
                "",
                "",
                "",
                "",
                "",
                "",
                LocalDate.of(2019, 3, 22),
                CinemaState.OFF,
                LocalDateTime.now()
        )

        def repertoireWhenPlayed = LocalDate.of(2022, 12, 23)
        def repertoireDto = new RepertoireDto(1,
                repertoireWhenPlayed,
                cinemaDto,
                null
        )
        def repertoire = new Repertoire(
                1,
                repertoireWhenPlayed,
                cinema)

        when:
        underTest.saveRepertoire(repertoireDto, 2)

        then:
        1 * cinemaRepository.findById(2) >> Optional.of(cinema)
        1 * repertoireRepository.save(repertoire) >> repertoire
        2 * logger.info(_)

        and:
        0 * _
    }

    def "test save repertoire without cinema id"() {
        given:
        def cinemaDto = new CinemaDto(
                2,
                "",
                "",
                "",
                "",
                "",
                "",
                LocalDate.of(2019, 3, 22),
                CinemaState.OFF,
                LocalDateTime.now()
        )
        def repertoireWhenPlayed = LocalDate.of(2022, 12, 23)
        def repertoireDto = new RepertoireDto(1,
                repertoireWhenPlayed,
                cinemaDto,
                null
        )

        when:
        underTest.saveRepertoire(repertoireDto, 2)

        then:
        1 * cinemaRepository.findById(2) >> Optional.empty()
        thrown RuntimeException
        2 * logger.info(_)

        and:
        0 * _
    }

    def "test get repertoires without any repertoires"() {
        when:
        underTest.getRepertoires()

        then:
        1 * repertoireRepository.findAll() >> _

        and:
        0 * _
    }

    def "test delete repertoire"() {
        when:
        underTest.deleteRepertoire(2)

        then:
        1 * filmRepository.deleteAllByRepertoireId(2)
        1 * repertoireRepository.deleteById(2)
        2 * logger.info(_)
    }

    def "test delete repertoire with wrong repertoire id"() {
        when:
        underTest.deleteRepertoire(2)

        then:
        1 * filmRepository.deleteAllByRepertoireId(2)
        1 * repertoireRepository.deleteById(2) >> { throw new EmptyResultDataAccessException("", 0, new RuntimeException("")) }
        thrown RuntimeException
        2 * logger.info(_)
    }
}