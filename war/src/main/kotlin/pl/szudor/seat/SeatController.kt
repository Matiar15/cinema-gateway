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
        description = "Creates a seat assigned to a room."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @PathVariable @Positive roomId: Int,
        @Valid @RequestBody payload: SeatPostPayload
    ) = seatService.saveSeat(roomId, payload.number!!).toDto()

    @Operation(
        summary = "Patch seat",
        description = "Patch status of the seat if the occupation changes."
    )
    @PatchMapping("/{id}")
    fun patch(
        @PathVariable @Positive roomId: Int,
        @PathVariable @Positive id: Int,
        @Valid @RequestBody payload: SeatPatchPayload
    ) = seatService.patchSeat(id, payload.occupied!!).toDto()

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