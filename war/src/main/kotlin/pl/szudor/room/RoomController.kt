package pl.szudor.room

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val roomService: RoomService
) {
    @PostMapping("/cinema/{cinemaId}")
    @ResponseStatus(HttpStatus.OK)
    fun save(@PathVariable @Positive cinemaId: Int, @Valid @RequestBody room: RoomDto): RoomDto =
        roomService.saveRoom(room, cinemaId).toDto()

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable @Positive id: Int, @RequestBody @Valid roomPayload: RoomPayload): RoomDto =
        roomService.updateRoom(id, roomPayload).toDto()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable @Positive id: Int) = roomService.deleteRoom(id)
}