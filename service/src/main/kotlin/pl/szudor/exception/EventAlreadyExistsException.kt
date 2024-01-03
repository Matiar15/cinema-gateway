package pl.szudor.exception

import pl.szudor.exception.generic.BadRequestException
import java.time.LocalTime

class EventAlreadyExistsException(playedAt: LocalTime) :
    BadRequestException("Date $playedAt is already taken by some other event in this room, and repertoire")
