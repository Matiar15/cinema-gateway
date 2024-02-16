package pl.szudor.utils

import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Pattern(regexp = "\\+([0-9]{2,3})-([0-9]{3})-([0-9]{3})-([0-9]{3})\$")
@NotNull
annotation class PhoneNumber(
    val message: String = "Passed phone number was not correct.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)