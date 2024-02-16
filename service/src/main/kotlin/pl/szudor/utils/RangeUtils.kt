package pl.szudor.utils

import com.google.common.collect.Range
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.ComparableExpression


/**
 *
 * @param T type that implements comparable interface
 * @param C type that implements comparable expression
 * @return Predicate created with provided Range
 * @author Mateusz Sidor
 */
fun <T: Comparable<T>, C: ComparableExpression<T>> Range<T>.between(root: C): Predicate? =
    when {
        hasLowerBound() && hasUpperBound() -> root.between(lowerEndpoint(), upperEndpoint())
        hasLowerBound() -> root.goe(lowerEndpoint())
        hasUpperBound() -> root.loe(upperEndpoint())
        else -> null
    }
