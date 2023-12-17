package pl.szudor.event

import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.cinema.toDto
import pl.szudor.film.Film
import pl.szudor.film.asFilter
import pl.szudor.repertoire.Repertoire
import pl.szudor.repertoire.asFilter
import pl.szudor.room.Room
import pl.szudor.utils.asGRange
import java.time.LocalTime
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/repertoire/{repertoireId}/film/{filmId}/room/{roomId}/event")
@Validated
class EventController(
    private val eventService: EventService,
) {
    @Operation(
        summary = "Save event",
        description = "Save an event."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @PathVariable @Positive repertoireId: Int,
        @PathVariable @Positive filmId: Int,
        @PathVariable @Positive roomId: Int,
        @RequestBody @Valid payload: EventPayload,
    ) = eventService.create(repertoireId, filmId, roomId, payload.playedAt!!).toDto()

    @Operation(
        summary = "Fetch all by filter",
        description = "Get events by filters."
    )
    @GetMapping
    fun fetchByFilter(
        @PathVariable @Positive repertoireId: Int,
        @PathVariable @Positive filmId: Int,
        @PathVariable @Positive roomId: Int,
        filter: EventFilterPayload,
        request: Pageable,
    ): Page<EventDto> = eventService.fetchByFilter(filter.asFilter(), request).map { it.toDto() }

    @Operation(
        summary = "Patch event",
        description = "Change event's played at time. This only can happen when date isn't already taken."
    )
    @PatchMapping("/{playedAt}")
    fun patch(
        @PathVariable @Positive repertoireId: Int,
        @PathVariable @Positive filmId: Int,
        @PathVariable @Positive roomId: Int,
        @PathVariable @DateTimeFormat(pattern = "HH:mm") playedAt: LocalTime,
        @RequestBody @Valid payload: EventPatchPayload,
    ) = eventService.patch(repertoireId, filmId, roomId, playedAt, payload.playedAt!!)

    @Operation(
        summary = "Delete event",
        description = "Delete an event."
    )
    @DeleteMapping("/{playedAt}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable @Positive repertoireId: Int,
        @PathVariable @Positive filmId: Int,
        @PathVariable @Positive roomId: Int,
        @PathVariable @DateTimeFormat(pattern = "HH:mm") playedAt: LocalTime,
    ) = eventService.delete(repertoireId, filmId, roomId, playedAt)

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
}