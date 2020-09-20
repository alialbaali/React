package com.shopping.common.domain.model.valueObject

data class Review(
    val customerId: String,
    val rating: Rating,
    val description: String? = null,
    val creationDate: LocalDate = LocalDate(),
)

enum class Rating {
    One, Two, Three, Four, Five
}
