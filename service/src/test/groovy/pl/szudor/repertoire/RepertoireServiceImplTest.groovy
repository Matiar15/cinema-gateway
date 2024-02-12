package pl.szudor.repertoire


import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaRepository
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.exception.RepertoireAlreadyPlayedAtException
import pl.szudor.exception.RepertoireNotExistsException
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class RepertoireServiceImplTest extends Specification {
    CinemaRepository cinemaRepository = Mock()
    RepertoireRepository repertoireRepository = Mock()
    RepertoireFactory repertoireFactory = Mock()

    def underTest = new RepertoireServiceImpl(repertoireRepository, repertoireFactory,  cinemaRepository)

    def cin = new Cinema().tap {
        it.id = 1
        it.name = ""
        it.address = ""
        it.nipCode = ""
        it.email = ""
        it.phoneNumber = ""
        it.postalCode = ""
        it.director = ""
        it.buildDate = LocalDate.of(2019, 3, 22)
        it.createdAt = LocalDateTime.of(2023, 3, 3, 3, 3)
        it.active = false
    }

    def played = LocalDate.of(2023, 3, 3)

    def rep = new Repertoire().tap {
        it.playedAt = played
        it.cinema = cin
        it.createdAt = LocalDateTime.of(2023, 3, 3, 3, 3)
    }

    def savedRep = new Repertoire().tap {
        it.id = 1
        it.playedAt = played
        it.cinema = cin
        it.createdAt = LocalDateTime.of(2023, 3, 3, 3, 3)
    }

    def "save repertoire all good"() {
        when:
        underTest.createRepertoire(2, played)

        then:
        1 * cinemaRepository.findById(2) >> Optional.of(cin)

        and:
        1 * repertoireFactory.createRepertoire(cin, played)
            >> rep

        and:
        1 * repertoireRepository.save(rep) >> savedRep

        and:
        0 * _
    }

    def "save repertoire without found cinema"() {
        when:
        underTest.createRepertoire(2, played)

        then:
        1 * cinemaRepository.findById(2) >> Optional.empty()

        and:
        thrown CinemaNotExistsException
        0 * _
    }

    def "patch repertoire with taken played at term"() {
        when:
        underTest.patchRepertoire(1, played)

        then:
        1 * repertoireRepository.findOneByPlayedAt(played)
            >> rep

        and:
        thrown RepertoireAlreadyPlayedAtException
        0 * _
    }

    def "patch repertoire without taken played at term but not found repertoire"() {
        when:
        underTest.patchRepertoire(1, played)

        then:
        1 * repertoireRepository.findOneByPlayedAt(played)
                >> null

        and:
        1 * repertoireRepository.findById(1) >> Optional.empty()

        and:
        thrown RepertoireNotExistsException
        0 * _
    }

    def "patch repertoire without taken played at term"() {
        given:
        def changedPlayedAt = LocalDate.of(2023, 3, 29)
        when:
        def result = underTest.patchRepertoire(1, changedPlayedAt)

        then:
        1 * repertoireRepository.findOneByPlayedAt(changedPlayedAt)
                >> null

        and:
        1 * repertoireRepository.findById(1) >> Optional.of(savedRep)

        and:
        result.playedAt == changedPlayedAt

        and:
        0 * _
    }

    def "get repertoires without any repertoires"() {
        given:
        def filter = new RepertoireFilter(null)
        def pageable = Mock(Pageable)

        when:
        underTest.fetchByFilter(1, filter, pageable)

        then:
        1 * repertoireRepository.fetchByFilter(1, filter, pageable)
                >> new PageImpl<Repertoire>([])

        and:
        0 * _
    }
}
