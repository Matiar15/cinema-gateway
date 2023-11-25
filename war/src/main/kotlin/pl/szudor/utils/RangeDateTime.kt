package pl.szudor.utils

import java.time.LocalDateTime
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [RangeDateTimeValidator::class])
annotation class RangeDateTimeConstraint(
    val message: String = "Lower bound must be less than the upper bound.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
) {
}

class RangeDateTimeValidator: ConstraintValidator<RangeDateTimeConstraint, RangeDto<LocalDateTime>> {
    override fun isValid(value: RangeDto<LocalDateTime>?, context: ConstraintValidatorContext?): Boolean =
        value.compareLowerBoundToUpper()
}