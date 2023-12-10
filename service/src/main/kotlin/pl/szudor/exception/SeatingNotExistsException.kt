package pl.szudor.exception

class SeatingNotExistsException(id: Int) :
    NotExistsException("Seating under ID: $id does not exist!")