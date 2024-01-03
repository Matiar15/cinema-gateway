package pl.szudor.seat

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.utils.toDto
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/room/{roomId}/seat")
@Validated
class SeatController(
    private val seatService: SeatService
) {
    @Operation(
        summary = "Create seat",
        description = "Creates seat by provided room ID"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @PathVariable @Positive roomId: Int,
        @Valid @RequestBody payload: SeatPayload
    ) = seatService.saveSeat(roomId, payload.number!!).toDto()

    @Operation(
        summary = "Delete seat",
        description = "Deletes seat with given ID"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable @Positive roomId: Int,
        @PathVariable @Positive id: Int
    ) = seatService.deleteSeat(id)
}