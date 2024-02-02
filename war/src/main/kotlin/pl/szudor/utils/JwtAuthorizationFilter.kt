package pl.szudor.utils

import io.jsonwebtoken.Claims
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthorizationFilter(
    private val jwtTokenManager: JwtTokenManager,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        jwtTokenManager.resolveClaims(request)?.also {
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
        filterChain.doFilter(request, response)
    }
}

fun Claims.asGrantedAuthority(): Collection<SimpleGrantedAuthorityConverter> =
    (entries.find { it.key == "roles" }?.value as? ArrayList<*>)?.map { SimpleGrantedAuthorityConverter(it) } ?: setOf()

class SimpleGrantedAuthorityConverter(private val auth: Any) : GrantedAuthority {
    override fun getAuthority(): String = (auth.toString())
}