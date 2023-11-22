package pl.szudor.exception

import pl.szudor.exception.generic.NotExistsException

class RepertoireNotExistsException(id: Int) : NotExistsException("Repertoire under ID: $id does not exist!")