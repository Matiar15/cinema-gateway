package pl.szudor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"pl.szudor", "pl.szudor.cinema"})
public class CinemaApp {
    public static void main(String[] args) {
        SpringApplication.run(CinemaApp.class, args);
    }
}
