package model

import com.shopping.common.domain.model.valueObject.*

private const val CustomerDefaultImageUrl = "https://res.cloudinary.com/shopping-cloud/image/upload/v1598449571/placeHolder_elsjfb.png"

data class Customer(
    val id: String,
    val name: String,
    val email: Email,
    val password: Password,
    val imageUrl: String = CustomerDefaultImageUrl,
    val cart: Cart = Cart.Empty,
    val addresses: Set<Address> = setOf(),
    val cards: Set<Card> = setOf(),
    val creationDate: LocalDate = LocalDate(),
)
