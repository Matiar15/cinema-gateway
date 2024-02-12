package pl.szudor.utils

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class BooleanConverter : AttributeConverter<Boolean, String> {
    override fun convertToDatabaseColumn(value: Boolean?): String =
        value?.let { "YES" } ?: "NO"


    override fun convertToEntityAttribute(value: String): Boolean =
        when (value) {
            "YES" -> true
            else -> false
        }

}