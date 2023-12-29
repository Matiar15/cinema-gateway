package pl.szudor.cinema

import javax.validation.constraints.NotNull

data class CinemaPayload(@field:NotNull val cinemaState: CinemaState?)