package pl.szudor.film

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class FilmFilterDto(
    @field:NotNull(groups = [UpdateValidation::class])
    val playedAt: LocalTime?,
    @field:NotNull(groups = [UpdateValidation::class])
    val title: String?,
    @field:NotNull(groups = [UpdateValidation::class])
    val pegi: Pegi?,
    @field:NotNull(groups = [UpdateValidation::class])
    val duration: Int?,
    @field:NotNull(groups = [UpdateValidation::class])
    val releaseDate: LocalDate?,
    @field:NotNull(groups = [UpdateValidation::class])
    val originalLanguage: String?,
    val createdAt: LocalDateTime?,
    @field:Valid
    val repertoire: RepertoireFilterDto,
    @field:Valid
    val room: RoomFilterDto
)

data class RoomFilterDto(
    @Positive(groups = [PatchValidation::class])
    val id: Int?
)

data class RepertoireFilterDto(
    @Positive(groups = [PatchValidation::class])
    val id: Int?
)

interface UpdateValidation
interface PatchValidation