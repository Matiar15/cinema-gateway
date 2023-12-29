package pl.szudor.utils

import java.time.LocalDate
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PositiveDateConstraint::class])
annotation class PositiveDate(
    val message: String = "Passed date cannot be older than current date.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)


class PositiveDateConstraint: ConstraintValidator<PositiveDate, LocalDate> {
    override fun isValid(value: LocalDate?, context: ConstraintValidatorContext?): Boolean =
        value?.let { !it.isBefore(LocalDate.now()) } ?: false
}