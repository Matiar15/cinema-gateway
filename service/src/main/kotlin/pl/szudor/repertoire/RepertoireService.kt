package pl.szudor.repertoire

import org.springframework.stereotype.Service
import pl.szudor.cinema.toCinema
import pl.szudor.cinema.toDto


interface RepertoireService  {
    fun saveRepertoire(repertoire: RepertoireDto): Repertoire
    fun deleteRepertoire()

}

@Service
class RepertoireServiceImpl(
    private val repertoireRepository: RepertoireRepository
) : RepertoireService {
    override fun saveRepertoire(repertoire: RepertoireDto): Repertoire {
        return repertoireRepository.save(repertoire.toEntity())
    }

    override fun deleteRepertoire() {
        TODO("Not yet implemented")
    }
}

fun Repertoire.toDto() =
    RepertoireDto(
        id,
        whenPlayed,
        cinema!!.toDto()
    )

fun RepertoireDto.toEntity() =
    Repertoire(
        id,
        whenPlayed!!,
        cinema?.toCinema()
    )
