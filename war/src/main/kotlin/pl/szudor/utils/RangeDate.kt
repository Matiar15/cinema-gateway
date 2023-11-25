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
@Constraint(validatedBy = [RangeDateValidator::class])
annotation class RangeDateConstraint(
    val message: String = "Lower bound must be less than the upper bound.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class RangeDateValidator: ConstraintValidator<RangeDateConstraint, RangeDto<LocalDate>> {
    override fun isValid(value: RangeDto<LocalDate>?, context: ConstraintValidatorContext?): Boolean =
        value.compareLowerBoundToUpper()

}