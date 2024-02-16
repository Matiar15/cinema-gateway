package pl.szudor;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@ComponentScan(basePackages = "pl.szudor")
public class CinemaConfig {
}
