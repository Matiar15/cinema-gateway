package pl.szudor.utils

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.collect.Range
import com.google.common.collect.Range.closed

data class RangeDto<T>(
    val from: T?,
    val to: T?
)
fun <T: Comparable<T>> RangeDto<T>.asGRange(): Range<T> = closed(from!!, to!!)

