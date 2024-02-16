package pl.szudor

import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import pl.szudor.auth.JwtTokenManager
import spock.mock.DetachedMockFactory

@Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
class ControllerTestConfig {
    def detachedMockFactory = new DetachedMockFactory()

    @Bean
    JwtTokenManager jwtTokenManager() {
        return detachedMockFactory.Mock(JwtTokenManager.class)
    }
}
