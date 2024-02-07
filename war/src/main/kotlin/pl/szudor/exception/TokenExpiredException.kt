package pl.szudor.exception

import pl.szudor.exception.generic.BadRequestException

class TokenExpiredException : BadRequestException("Provided token has expired")