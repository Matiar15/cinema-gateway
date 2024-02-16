package pl.szudor.utils

import pl.szudor.cinema.*
import pl.szudor.event.Event
import pl.szudor.event.EventDto
import pl.szudor.event.EventFilter
import pl.szudor.event.EventFilterPayload
import pl.szudor.film.Film
import pl.szudor.film.FilmDto
import pl.szudor.film.FilmFilter
import pl.szudor.film.FilmFilterPayload
import pl.szudor.repertoire.Repertoire
import pl.szudor.repertoire.RepertoireDto
import pl.szudor.repertoire.RepertoireFilter
import pl.szudor.repertoire.RepertoireFilterPayload
import pl.szudor.room.Room
import pl.szudor.room.RoomDto
import pl.szudor.seat.Seat
import pl.szudor.seat.SeatDto

fun Seat.toDto() = SeatDto(
    id!!,
    number!!
)

fun RepertoireFilterPayload.asFilter() = RepertoireFilter(playedAt?.asGRange())

fun FilmFilterPayload.asFilter() = FilmFilter(
    playedAt?.asGRange(),
    title,
    pegi,
    duration?.asGRange(),
    releaseDate?.asGRange(),
    originalLanguage,
    createdAt?.asGRange()
)

fun Event.toDto() = EventDto(
    repertoire!!.toDto(),
    film!!.toDto(),
    room!!.toDto(),
    playedAt!!
)

fun Repertoire.toDto() = RepertoireDto(
    id!!,
    playedAt!!,
    cinema!!.toDto()
)

fun Film.toDto() = FilmDto(
    id!!,
    title!!,
    pegi!!,
    duration!!,
    originalLanguage!!
)

fun Room.toDto() = RoomDto(
    id!!, number!!
)

fun EventFilterPayload.asFilter() = EventFilter(
    repertoire?.asFilter(),
    film?.asFilter(),
    playedAt?.asGRange()
)

fun CinemaFilterPayload.asFilter() =
    CinemaFilter(
        name,
        address,
        email,
        phoneNumber,
        postalCode,
        director,
        nipCode,
        buildDate?.asGRange(),
        active,
        createdAt?.asGRange()
    )

fun Cinema.toDto() =
    CinemaDto(
        id!!,
        name!!,
        address!!,
        email!!,
        phoneNumber!!,
        postalCode!!,
        director!!,
        nipCode!!,
        buildDate!!,
        active!!,
        createdAt!!
    )