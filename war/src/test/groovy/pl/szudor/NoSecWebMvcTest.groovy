package pl.szudor

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.annotation.AliasFor

import java.lang.annotation.Documented
import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
@interface NoSecWebMvcTest {

    @AliasFor(annotation = WebMvcTest.class, attribute = "value")
    Class value();

}