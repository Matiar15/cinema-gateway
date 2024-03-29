package pl.szudor.room

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.utils.toDto
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/cinema/{cinemaId}/room")
@Validated
class RoomController(
    private val roomService: RoomService
) {
    @Operation(
        summary = "Create a room",
        description = "Creates a room in given cinema and room number."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @PathVariable @Positive cinemaId: Int,
        @RequestBody @Valid payload: RoomPayload
    ) = roomService.saveRoom(payload.number!!, cinemaId).toDto()

    @Operation(
        summary = "Delete room",
        description = "Deletes a room in given cinema."
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable @Positive cinemaId: Int,
        @PathVariable @Positive id: Int
    ) = roomService.deleteRoom(id)
}

