package pl.szudor.exception

class RepertoireNotExistsException(id: Int) :
    NotExistsException("Repertoire under ID: $id does not exist!")