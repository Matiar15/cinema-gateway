package pl.szudor.exception

class FilmNotExistsException(id: Int) : NotExistsException("Film under ID: $id does not exist!")