package com.shopping.common.domain.model.valueObject

data class Card(
    val brand: String = String(),
    val number: Long,
    val expirationDate: LocalDate,
    val cvc: Long = 0,
)
class LocalDate