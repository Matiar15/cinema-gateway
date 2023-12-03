package pl.szudor.cinema

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

interface CinemaFactory {
    fun createCinema(
        name: String,
        address: String,
        email: String,
        phoneNumber: String,
        postalCode: String,
        director: String,
        nipCode: String,
        buildDate: LocalDate
    ): Cinema
}

@Service
class CinemaFactoryImpl : CinemaFactory {
    override fun createCinema(
        name: String,
        address: String,
        email: String,
        phoneNumber: String,
        postalCode: String,
        director: String,
        nipCode: String,
        buildDate: LocalDate
    ): Cinema = Cinema().apply {
        this.name = name
        this.address = address
        this.email = email
        this.phoneNumber = phoneNumber
        this.postalCode = postalCode
        this.director = director
        this.nipCode = nipCode
        this.buildDate = buildDate
        this.state = State.NO
        this.createdAt = LocalDateTime.now()
    }
}