package pl.szudor.utils

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [RangeIntValidator::class])
annotation class RangeInt(
    val message: String = "Lower bound must be less than the upper bound.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class RangeIntValidator : ConstraintValidator<RangeInt, RangeDto<Int>> {
    override fun isValid(value: RangeDto<Int>?, context: ConstraintValidatorContext?): Boolean =
        value.compareLowerBoundToUpper()
}