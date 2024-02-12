package pl.szudor.exception

import pl.szudor.exception.generic.BadRequestException

class BadCredentialsException : BadRequestException("Provided credentials were not correct.")