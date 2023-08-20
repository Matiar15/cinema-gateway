package pl.szudor.exception

class RepertoireNotExistsException(id: Int) :
    NotExistsException("Repertoire id not found : $id")