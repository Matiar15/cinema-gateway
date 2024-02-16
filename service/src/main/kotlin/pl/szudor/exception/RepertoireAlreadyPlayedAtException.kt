package pl.szudor.exception

import pl.szudor.exception.generic.BadRequestException
import java.time.LocalDate

class RepertoireAlreadyPlayedAtException(playedAt: LocalDate) :
    BadRequestException("Repertoire played date cannot be changed. Date $playedAt is already taken!")