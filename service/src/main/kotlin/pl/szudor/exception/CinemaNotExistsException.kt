package pl.szudor.exception

class CinemaNotExistsException(id: Int) : NotExistsException("Cinema under ID: $id does not exist!")