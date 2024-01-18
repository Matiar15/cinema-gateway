package pl.szudor.exception

import pl.szudor.exception.generic.NotExistsException

class SeatNotExistsException(id: Int) : NotExistsException("Seat under ID: $id does not exist!")