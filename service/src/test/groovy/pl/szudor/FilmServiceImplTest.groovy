package pl.szudor

import net.sf.cglib.core.Local
import org.slf4j.Logger
import org.springframework.dao.EmptyResultDataAccessException
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaDto
import pl.szudor.cinema.CinemaState
import pl.szudor.exception.RepertoireNotExistsException
import pl.szudor.film.Film
import pl.szudor.film.FilmDto
import pl.szudor.film.FilmRepository
import pl.szudor.film.FilmServiceImpl
import pl.szudor.film.Pegi
import pl.szudor.repertoire.Repertoire
import pl.szudor.repertoire.RepertoireDto
import pl.szudor.repertoire.RepertoireRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

class FilmServiceImplTest extends Specification {
    def filmRepository = Mock(FilmRepository)
    def repertoireRepository = Mock(RepertoireRepository)
    def underTest = new FilmServiceImpl(filmRepository, repertoireRepository)
    def logger = underTest.logger = Mock(Logger)

    def "test save film"() {
        given:
        def filmDto = new FilmDto(
                1,
                LocalTime.of(12, 30),
                10,
                new RepertoireDto(2, LocalDate.of(2019, 3, 3), null, null),
                "",
                Pegi.EIGHTEEN,
                1,
                LocalDate.of(2019, 3, 3),
                "",
                null
        )
        def film = new Film(1,
                LocalTime.of(12, 30),
                10,
                new Repertoire(2, LocalDate.of(2019, 3, 3), null),
                "",
                Pegi.EIGHTEEN,
                1,
                LocalDate.of(2019, 3, 3),
                "")
        when:
        underTest.saveFilm(filmDto, 2)

        then:
        1 * filmRepository.save(film) >> film
        1 * repertoireRepository.findById(2) >> Optional.of(new Repertoire(2, LocalDate.of(2019, 3, 3), null))
        2 * logger.info(_)

        and:
        0 * _
    }

    def "test save film with thrown exception"() {
        given:
        def filmDto = new FilmDto(
                1,
                LocalTime.of(12, 30),
                10,
                null,
                "",
                Pegi.EIGHTEEN,
                1,
                LocalDate.of(2019, 3, 3),
                "",
                null
        )

        when:
        underTest.saveFilm(filmDto, 2)

        then:
        1 * repertoireRepository.findById(2) >> Optional.empty()
        thrown RepertoireNotExistsException
        2 * logger.info(_)

        and:
        0 * _
    }

    def "test get films"() {
        when:
        underTest.getFilms()

        then:
        1 * filmRepository.findAll() >> _

        and:
        0 * _
    }

    def "test delete film"() {
        when:
        underTest.deleteFilm(2)

        then:
        1 * filmRepository.deleteById(2) >> _
        1 * logger.info(_)

        and:
        0 * _
    }

    def "test delete film with wrong film id"() {
        when:
        underTest.deleteFilm(2)

        then:
        1 * filmRepository.deleteById(2) >> { throw new EmptyResultDataAccessException("", 0, new RuntimeException()) }
        thrown RuntimeException
        1 * logger.info(_)

        and:
        0 * _
    }

}
