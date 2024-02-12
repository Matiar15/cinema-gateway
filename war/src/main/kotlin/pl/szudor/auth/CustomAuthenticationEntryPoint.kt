package pl.szudor.auth

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import pl.szudor.exception.ErrorDto
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : BasicAuthenticationEntryPoint() {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        response.apply {
            contentType = "application/json"
            characterEncoding = "UTF-8"
            setHeader("WWW-Authenticate", "Basic realm=$realmName")
            status = HttpServletResponse.SC_UNAUTHORIZED
        }
        val writer = response.writer
        writer.println(
            objectMapper
                .writeValueAsString(ErrorDto(authException.message!!).toResponseEntity(HttpStatus.UNAUTHORIZED).body)
        )
        writer.flush()
    }

    override fun afterPropertiesSet() {
        realmName = "Cinema-Gateway"
        super.afterPropertiesSet()
    }
}