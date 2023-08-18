package pl.szudor


import org.slf4j.Logger
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

    def "test store repertoire"() {
        given:
        underTest.logger = Mock(Logger)

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
        2 * underTest.logger.info(_)

        and:
        0 * _
    }
}
