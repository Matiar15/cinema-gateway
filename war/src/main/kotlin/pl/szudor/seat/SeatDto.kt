package pl.szudor.seat

data class SeatDto(
    val id: Int,
    val number: Int,
    val occupied: Occupied
)