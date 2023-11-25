package pl.szudor.cinema

import javax.validation.constraints.NotNull

data class CinemaPatchPayload(@field:NotNull val state: State?)