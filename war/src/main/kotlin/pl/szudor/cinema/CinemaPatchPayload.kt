package pl.szudor.cinema

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

data class CinemaPatchPayload(
    @field:Schema(description = "New state")
    @field:NotNull
    val active: Boolean?
)