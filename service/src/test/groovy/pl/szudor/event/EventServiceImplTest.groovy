package pl.szudor.event

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import pl.szudor.cinema.Cinema
import pl.szudor.exception.*
import pl.szudor.film.Film
import pl.szudor.film.FilmRepository
import pl.szudor.film.Pegi
import pl.szudor.repertoire.Repertoire
import pl.szudor.repertoire.RepertoireRepository
import pl.szudor.room.Room
import pl.szudor.room.RoomRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class EventServiceImplTest extends Specification {
    EventFactory eventFactory = Mock()
    EventRepository eventRepository = Mock()
    RepertoireRepository repertoireRepository = Mock()
    FilmRepository filmRepository = Mock()
    RoomRepository roomRepository = Mock()
    def underTest = new EventServiceImpl(eventFactory, eventRepository, repertoireRepository, filmRepository, roomRepository)

    private final int repertoireId = 1
    private final int filmId = 2
    private final int roomId = 3
    private final int event_id = 4
    private final LocalTime played_at = LocalTime.of(12, 35)

    def cinema_ = new Cinema().tap {
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

    def repertoire_ = new Repertoire().tap {
        it.id = 1
        it.playedAt = played
        it.cinema = cinema_
        it.createdAt = LocalDateTime.of(2023, 3, 3, 3, 3)
    }

    def time = LocalTime.of(23, 3)
    def peg = Pegi.SEVEN
    def date = LocalDate.of(2023, 3, 3)
    def dateTime = LocalDateTime.of(date, time)

    def film_ = new Film().tap {
        it.id = 2
        it.createdAt = dateTime
        it.duration = 12
        it.originalLanguage = ""
        it.pegi = peg
        it.title = ""
        it.releaseDate = date
    }

    def room_ = new Room().tap {
        it.id = 3
        it.number = 3
        it.cinema = cinema_
    }

    def event = new Event().tap {
        it.id = 4
        it.playedAt = played_at
        it.room = room_
        it.film = film_
        it.repertoire = repertoire_
    }

    def "create event"() {
        when:
        underTest.create(repertoireId, filmId, roomId, played_at)

        then:
        1 * eventRepository.findByRepertoireAndRoomAndPlayedAt(repertoireId, roomId, played_at)
            >> null

        and:
        1 * repertoireRepository.findById(repertoireId)
            >> Optional.of(repertoire_)

        and:
        1 * filmRepository.findById(filmId)
            >> Optional.of(film_)

        and:
        1 * roomRepository.findById(roomId)
            >> Optional.of(room_)

        and:
        1 * eventFactory.createEvent(repertoire_, film_, room_, played_at)
            >> event

        and:
        1 * eventRepository.save(event) >> event

        and:
        0 * _
    }

    def "create event with thrown repertoire exception"() {
        when:
        underTest.create(repertoireId, filmId, roomId, played_at)

        then:
        1 * eventRepository.findByRepertoireAndRoomAndPlayedAt(repertoireId, roomId, played_at)
                >> null

        and:
        1 * repertoireRepository.findById(repertoireId)
                >> Optional.empty()

        and:
        thrown RepertoireNotExistsException
        0 * _
    }

    def "create event with thrown film exception"() {
        when:
        underTest.create(repertoireId, filmId, roomId, played_at)

        then:
        1 * eventRepository.findByRepertoireAndRoomAndPlayedAt(repertoireId, roomId, played_at)
                >> null

        and:
        1 * repertoireRepository.findById(repertoireId)
                >> Optional.of(repertoire_)

        and:
        1 * filmRepository.findById(filmId)
                >> Optional.empty()

        and:
        thrown FilmNotExistsException
        0 * _
    }

    def "create event with thrown room exception"() {
        when:
        underTest.create(repertoireId, filmId, roomId, played_at)

        then:
        1 * eventRepository.findByRepertoireAndRoomAndPlayedAt(repertoireId, roomId, played_at)
                >> null

        and:
        1 * repertoireRepository.findById(repertoireId)
                >> Optional.of(repertoire_)

        and:
        1 * filmRepository.findById(filmId)
                >> Optional.of(film_)

        and:
        1 * roomRepository.findById(roomId)
                >> Optional.empty()

        and:
        thrown RoomNotExistsException
        0 * _
    }

    def "create event with found event"() {
        when:
        underTest.create(repertoireId, filmId, roomId, played_at)

        then:
        1 * eventRepository.findByRepertoireAndRoomAndPlayedAt(repertoireId, roomId, played_at)
                >> event

        and:
        thrown EventAlreadyExistsException
        0 * _
    }


    def "patch event all good"() {
        when:
        underTest.patch(event_id, repertoireId, roomId, played_at)

        then:
        1 * eventRepository.findByRepertoireAndRoomAndPlayedAt(repertoireId, roomId, played_at)
                >> null

        and:
        1 * eventRepository.findById(event_id) >> Optional.of(event)

        and:
        0 * _
    }

    def "patch event found event that already has that date"() {
        when:
        underTest.patch(event_id, repertoireId, roomId, played_at)

        then:
        1 * eventRepository.findByRepertoireAndRoomAndPlayedAt(repertoireId, roomId, played_at)
                >> event

        and:
        thrown EventAlreadyExistsException
        0 * _
    }

    def "patch event not found event"() {
        when:
        underTest.patch(event_id, repertoireId, roomId, played_at)

        then:
        1 * eventRepository.findByRepertoireAndRoomAndPlayedAt(repertoireId, roomId, played_at)
                >> null

        and:
        1 * eventRepository.findById(event_id)
            >> Optional.empty()

        and:
        thrown EventNotExistsException
        0 * _
    }

    def "fetch by filter"() {
        given:
        def filter = new EventFilter(null, null, null)
        def request = Mock(Pageable)

        when:
        underTest.fetchByFilter(filter, request)

        then:
        1 * eventRepository.fetchByFilter(filter, request)
            >> new PageImpl<Event>([])

        and:
        0 * _
    }
}
