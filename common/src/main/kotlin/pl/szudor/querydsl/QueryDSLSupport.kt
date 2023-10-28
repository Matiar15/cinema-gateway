package pl.szudor.querydsl

import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.Querydsl

fun <T> Querydsl.applyPagination(pageable: Pageable, query: JPQLQuery<T>, count: () -> Long) =
    PageImpl<T>(
        query.apply { offset(pageable.offset); limit(pageable.pageSize.toLong()) }.fetch(),
        pageable,
        count.invoke()
    )