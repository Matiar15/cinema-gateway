package pl.szudor.seating

import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/seats")
class SeatingController(
    private val seatingService: SeatingService
) {
    @PostMapping("/room/{roomId}")
    fun create(@PathVariable @Positive roomId: Int, @Valid @RequestBody seating: SeatingDto): SeatingDto =
        seatingService.saveSeating(seating, roomId).toDto()

    @PutMapping("/{id}")
    fun update(@PathVariable @Positive id: Int, @Valid @RequestBody seating: SeatingDto): SeatingDto =
        seatingService.updateSeating(id, seating).toDto()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable @Positive id: Int) = seatingService.deleteSeating(id)
}