package pl.szudor.exception

import pl.szudor.exception.generic.BadRequestException

class SignatureException : BadRequestException("Signature does not match locally computed signature.")