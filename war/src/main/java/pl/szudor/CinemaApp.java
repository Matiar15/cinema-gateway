package pl.szudor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"pl.szudor.cinema", "pl.szudor"} )
public class CinemaApp {
    public static void main(String... args) {
        SpringApplication.run(CinemaApp.class, args);
    }

}