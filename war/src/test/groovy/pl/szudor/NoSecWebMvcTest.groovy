package pl.szudor

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.core.annotation.AliasFor
import pl.szudor.auth.JwtAuthorizationFilter

import java.lang.annotation.Documented
import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class, excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = [JwtAuthorizationFilter.class]))
@interface NoSecWebMvcTest {

    @AliasFor(annotation = WebMvcTest.class, attribute = "value")
    Class value();

}