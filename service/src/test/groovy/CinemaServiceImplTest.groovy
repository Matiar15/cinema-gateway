import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.CinemaServiceImpl
import spock.lang.Specification

class CinemaServiceImplTest extends Specification {
    def cinemaRepository = Mock(CinemaRepository)
    def underTest = new CinemaServiceImpl(cinemaRepository)

    def "test store cinema"() {
        given:
        def test_cinema = new Cinema(
                1,
                "Cinema City",
                "Światowida",
                "Mike Wazowski",
                "+48-123-456-789",
                "12-345")

        when:
        underTest.storeCinema(test_cinema)

        then:
        1 * cinemaRepository.save(test_cinema)

        and:
        0 * _
    }

    def "test get all cinemas"() {
        when:
        underTest.getAllCinemas()

        then:
        1 * cinemaRepository.findAll() >> _

        and:
        0 * _
    }

}
