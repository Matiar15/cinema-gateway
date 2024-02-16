package pl.szudor.exception

import pl.szudor.exception.generic.NotExistsException

class CinemaNotExistsException(id: Int) : NotExistsException("Cinema under ID: $id does not exist!")