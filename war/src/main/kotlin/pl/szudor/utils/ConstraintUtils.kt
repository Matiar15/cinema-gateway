package pl.szudor.utils

/**
 * Constraint method for checking if given RangeDto has correct lower and upper bound.
 * Function returns true if params are null, if one of them is null.
 * But if from and to exists it must match a condition,
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
            if (this.from == null && this.to == null) true
            else if (this.from == null) true
            else if (this.to == null) true
            else this.from!!.compareTo(this.to!!) <= 1
        }
    }

/**
 * Class method creating com.google.common.collect.Range from given parameters, from and to.
 * @param T type that implements comparable interface
 * @see RangeDto
 * @author Mateusz Sidor
 */
fun <T : Comparable<T>> RangeDto<T>.asGRange(): com.google.common.collect.Range<T> {
    var range = com.google.common.collect.Range.all<T>()
    if (from != null && to != null) range = com.google.common.collect.Range.closed(from!!, to!!)
    else if (from != null) range = com.google.common.collect.Range.atLeast(from!!)
    else if (to != null) {
        range = com.google.common.collect.Range.atMost(to!!)
    }
    return range
}