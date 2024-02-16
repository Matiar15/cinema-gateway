package pl.szudor.exception

import pl.szudor.exception.generic.NotExistsException

class FilmNotExistsException(id: Int) : NotExistsException("Film under ID: $id does not exist!")