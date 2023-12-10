package pl.szudor.film

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class FilmServiceImplTest extends Specification {
    FilmRepository filmRepository = Mock()
    FilmFactory filmFactory = Mock()
    def underTest = new FilmServiceImpl(filmRepository, filmFactory)

    def time = LocalTime.of(23, 3)
    def peg = Pegi.SEVEN
    def date = LocalDate.of(2023, 3, 3)
    def dateTime = LocalDateTime.of(date, time)

    def film = new Film().tap {
        it.createdAt = dateTime
        it.duration = 12
        it.originalLanguage = ""
        it.pegi = peg
        it.title = ""
        it.releaseDate = date
        it.playedAt = time
    }

    def savedFilm = new Film().tap {
        it.id = 1
        it.createdAt = dateTime
        it.duration = 12
        it.originalLanguage = ""
        it.pegi = peg
        it.title = ""
        it.releaseDate = date
        it.playedAt = time
    }

    def filter = new FilmFilter(null, null, null, null, null, null, null)

    def "save film"() {
        when:
        underTest.saveFilm(time, "", peg, 12, date, "")

        then:
        1 * filmFactory.createFilm(time, "", peg, 12, date, "") >> film

        and:
        1 * filmRepository.save(film) >> savedFilm

        and:
        0 * _
    }


    def "get films"() {
        given:
        def pageable = Mock(Pageable)

        when:
        underTest.fetchByFilter(filter, pageable)

        then:
        1 * filmRepository.fetchByFilter(filter, pageable) >> new PageImpl<Film>([])

        and:
        0 * _
    }

    def "delete film"() {
        when:
        underTest.deleteFilm(2)

        then:
        1 * filmRepository.deleteById(2) >> _

        and:
        0 * _
    }

    def "delete film with wrong film id"() {
        when:
        underTest.deleteFilm(2)

        then:
        1 * filmRepository.deleteById(2) >> { throw new EmptyResultDataAccessException("", 0, new RuntimeException()) }

        and:
        thrown RuntimeException
        0 * _
    }

}
