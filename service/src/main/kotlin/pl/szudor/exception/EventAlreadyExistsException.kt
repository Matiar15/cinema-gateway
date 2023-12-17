package pl.szudor.exception

import pl.szudor.exception.generic.BadRequestException
import java.time.LocalTime

class EventAlreadyExistsException(oldPlayedAt: LocalTime) :
    BadRequestException("Date $oldPlayedAt is already taken by some other event")
