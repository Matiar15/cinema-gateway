package pl.szudor.utils

open class RangeDto<T : Comparable<T>>(
    open var from: T? = null,
    open var to: T? = null
) {
    class Int(from: kotlin.Int? = null, to: kotlin.Int? = null) : RangeDto<kotlin.Int>(from, to)
    class LocalDate(from: java.time.LocalDate? = null, to: java.time.LocalDate? = null) :
        RangeDto<java.time.LocalDate>(from, to)

    class LocalDateTime(from: java.time.LocalDateTime? = null, to: java.time.LocalDateTime? = null) :
        RangeDto<java.time.LocalDateTime>(from, to)
}