package model

import com.shopping.common.domain.model.valueObject.Address
import com.shopping.common.domain.model.valueObject.Card
import com.shopping.common.domain.model.valueObject.LocalDate

data class Order(
    val id: String,
    val customerId: String,
    val orderItems: Set<OrderItem>,
    val address: Address,
    val card: Card,
    val creationDate: LocalDate,
) {

    data class OrderItem(
        val productId: String,
        val quantity: Long,
    )
}
