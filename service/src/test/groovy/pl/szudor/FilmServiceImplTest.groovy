package pl.szudor

import org.slf4j.Logger
import org.springframework.dao.EmptyResultDataAccessException
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaState
import pl.szudor.exception.RepertoireNotExistsException
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.film.Film
import pl.szudor.film.FilmDto
import pl.szudor.film.FilmRepository
import pl.szudor.film.FilmServiceImpl
import pl.szudor.film.Pegi
import pl.szudor.repertoire.Repertoire
import pl.szudor.repertoire.RepertoireDto
import pl.szudor.repertoire.RepertoireRepository
import pl.szudor.room.Room
import pl.szudor.room.RoomDto
import pl.szudor.room.RoomRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

class FilmServiceImplTest extends Specification {
    def filmRepository = Mock(FilmRepository)
    def roomRepository = Mock(RoomRepository)
    def repertoireRepository = Mock(RepertoireRepository)
    def underTest = new FilmServiceImpl(filmRepository, roomRepository, repertoireRepository)
    def logger = underTest.logger = Mock(Logger)

    def "test save film"() {
        given:
        def repertoire = new Repertoire()
        def room = new Room(12, new Cinema(1, "", "", "", "", "", "", "", LocalDate.of(2023, 3, 3), CinemaState.ON))
        def filmDto = new FilmDto(
                1,
                LocalTime.of(12, 30),
                new RepertoireDto(2, LocalDate.of(2019, 3, 3), null, null),
                "asd",
                Pegi.EIGHTEEN,
                1,
                LocalDate.of(2019, 3, 3),
                "",
                null,
                null
        )
        def film = new Film(1,
                LocalTime.of(12, 30),
                new Repertoire(2, LocalDate.of(2019, 3, 3), null),
                "",
                Pegi.EIGHTEEN,
                1,
                LocalDate.of(2019, 3, 3),
                "",
                room)

        when:
        underTest.saveFilm(filmDto, 2, 3)

        then:
        1 * roomRepository.findById(3) >> Optional.of(room)
        1 * repertoireRepository.findById(2) >> Optional.of(repertoire)
        1 * filmRepository.save(film) >> film
        1 * logger.info(_)

        and:
        0 * _
    }

    def "test save film with thrown room exception"() {
        given:
        def filmDto = new FilmDto(
                1,
                LocalTime.of(12, 30),
                new RepertoireDto(2, LocalDate.of(2019, 3, 3), null, null),
                "asd",
                Pegi.EIGHTEEN,
                1,
                LocalDate.of(2019, 3, 3),
                "",
                new RoomDto(0, 0, null, null),
                null
        )

        when:
        underTest.saveFilm(filmDto, 2, 3)

        then:
        1 * roomRepository.findById(3) >> Optional.empty()

        thrown RoomNotExistsException
        1 * logger.info(_)

        and:
        0 * _
    }

    def "test save film with thrown repertoire exception"() {
        given:
        def room = new Room(12, new Cinema(1, "", "", "", "", "", "", "", LocalDate.of(2023, 3, 3), CinemaState.ON))
        def filmDto = new FilmDto(
                1,
                LocalTime.of(12, 30),
                new RepertoireDto(2, LocalDate.of(2019, 3, 3), null, null),
                "asd",
                Pegi.EIGHTEEN,
                1,
                LocalDate.of(2019, 3, 3),
                "",
                new RoomDto(0, 0, null, null),
                null
        )

        when:
        underTest.saveFilm(filmDto, 2, 3)

        then:
        1 * roomRepository.findById(3) >> Optional.of(room)
        1 * repertoireRepository.findById(2) >> Optional.empty()
        thrown RepertoireNotExistsException
        1 * logger.info(_)

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
