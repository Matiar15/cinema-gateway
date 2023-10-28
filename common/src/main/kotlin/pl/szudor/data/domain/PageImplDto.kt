package pl.szudor.data.domain

data class PageImplDto<T>(
    val content: List<T>,
    val pageable: PageableDto,
    val totalPages: String,
    val last: Boolean,
    val totalElements: Int,
    val size: Int,
    val number: Int,
    val sort: SortDto,
    val numberOfElements: Int,
    val first: Boolean,
    val empty: Boolean
)

data class SortDto(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class PageableDto(
    val sort: SortDto,
    val offset: Int,
    val pageSize: Int,
    val pageNumber: Int,
    val paged: Boolean,
    val unpaged: Boolean
)