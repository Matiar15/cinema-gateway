package pl.szudor.exception

class FilmNotExistsException(message: String, cause: Throwable) : NotExistsException(message, cause)