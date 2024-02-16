package pl.szudor.utils

import javax.validation.ConstraintViolationException

/**
 * Constraint method for checking if given RangeDto has correct lower and upper bound.
 * Function returns true if params are null, if one of them is null.
 * But if from and to exists, it must match a condition,
 * where to is lower than from.
 * @param T type that implements comparable interface
 * @see RangeDto
 * @param <T> target type of class
 *
 * @author Mateusz Sidor
 */
fun <T : Comparable<T>> RangeDto<T>?.compareLowerBoundToUpper(): Boolean =
    when (this) {
        null -> true
        else -> {
            if (this.from!!.compareTo(this.to!!) > 1)
                throw ConstraintViolationException("Lower bound must be less or equal than the upper bound!", null)
            else true
        }
    }

/**
 * Class method creating com.google.common.collect.Range from given parameters, from and to.
 * @param T type that implements comparable interface
 * @see RangeDto
 * @author Mateusz Sidor
 */
fun <T : Comparable<T>> RangeDto<T>.asGRange(): com.google.common.collect.Range<T> =
    when {
        from != null && to != null -> com.google.common.collect.Range.closed(from!!, to!!)
        from != null -> com.google.common.collect.Range.atLeast(from!!)
        to != null -> com.google.common.collect.Range.atMost(to!!)
        else -> com.google.common.collect.Range.all()
    }
