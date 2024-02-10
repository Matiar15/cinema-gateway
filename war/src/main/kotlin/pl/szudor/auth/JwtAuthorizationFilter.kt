package pl.szudor.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import pl.szudor.exception.TokenExpiredException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

<<<<<<< HEAD
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import pl.szudor.exception.TokenExpiredException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthorizationFilter(
    private val jwtTokenManager: JwtTokenManager,
    private val entryPoint: AuthenticationEntryPoint
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            resolveClaims(request)?.also {
                jwtTokenManager.validateClaims(it).apply {
                    val authentication: Authentication =
                        UsernamePasswordAuthenticationToken(
                            it,
                            "",
                            it.asGrantedAuthority()
                        )
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (ex: AuthenticationException) {
            entryPoint.commence(request, response, ex)
            return
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveClaims(req: HttpServletRequest): Claims? =
        resolveToken(req)?.let {
            try {
                Jwts.parser().verifyWith(JwtTokenManager.KEY).build().parseSignedClaims(it).payload
            } catch (ex: ExpiredJwtException) {
                throw TokenExpiredException()
            } catch (ex: SignatureException) {
                throw pl.szudor.exception.SignatureException()
            } catch (ex: MalformedJwtException) {
                throw pl.szudor.exception.MalformedTokenException()
            }
        }
    private fun resolveToken(request: HttpServletRequest): String? = request.getHeader(JwtTokenManager.TOKEN_HEADER)?.let {
        return when {
            (it.startsWith(JwtTokenManager.TOKEN_PREFIX)) -> it.substring(JwtTokenManager.TOKEN_PREFIX.length)
            else -> null
        }
    }
}

fun Claims.asGrantedAuthority(): Collection<SimpleGrantedAuthorityConverter> =
    (entries.find { it.key == "roles" }?.value as? ArrayList<*>)?.map { SimpleGrantedAuthorityConverter(it) } ?: setOf()

class SimpleGrantedAuthorityConverter(private val auth: Any) : GrantedAuthority {
    override fun getAuthority(): String = (auth.toString())
}