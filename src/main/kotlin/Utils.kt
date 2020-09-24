import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import model.Product
import react.*
import react.dom.fieldSet
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledH1

// Function that takes RBuilder to build a component just like a functionalComponent and returns an extension function on RBuilder
// which takes another props extension function

fun <P : RProps> RProps.emptyHandler(): Handler<P> = {}

val RProps.emptyHandler: RProps.() -> Unit
    get() = {}

typealias Handler<P> = P.() -> Unit

fun <P : RProps> fc(func: RBuilder.(P) -> Unit): RBuilder.(Handler<P>) -> ReactElement = { handler ->
    child(functionalComponent(func)) {
        attrs(handler)
    }
}

data class ProductState(val products: List<Product>)


val productList = fc<RProps> {

    val (state, setState) = useState { ProductState(emptyList()) }

    state.products.forEach {
        productItem {
            key = product.id
            product = it
            onClick = { id ->

            }
        }
    }


    container {
        container {
            productItem(emptyHandler())
            materialButton(emptyHandler())

            container {
                productItem(emptyHandler())
                materialButton(emptyHandler())
                materialButton(emptyHandler)
            }
        }
    }
    materialButton {
        text = "Click Me"
        onClick = {
            window.alert("Nice Click!")
        }
        textColor = Color.blue
    }
}

abstract class LayoutProps : RProps {
    abstract var text: String
    var textColor: Color
        get() = Color.white
        set(value) {}
    lateinit var onClick: () -> Unit
    var backgroundColor: Color = Color.white
}

interface MaterialTextProps : RProps {
    var text: String
    var textColor: Color
        get() = Color.white
        set(value) {}
    var onClick: () -> Unit
    var backgroundColor: Color
}

external interface MaterialButtonProps : RProps {
    var text: String?
    var textColor: Color?
    var onClick: () -> Unit?
    var backgroundColor: Color
}

val materialButton = fc<MaterialButtonProps> { props ->
    styledButton {
        +(props.text ?: "")
        css {
            color = props.textColor ?: Color.blue
            backgroundColor = props.backgroundColor
        }
        attrs {
            onClickFunction = { props.onClick() }
        }
    }
}


val container = fc<RProps> {
    styledDiv {
        css {
            display = Display.flex
        }
    }
}

external interface ProductProps : RProps {
    var product: Product
    var onClick: (id: String) -> Unit
}

val productItem = fc<ProductProps> { productProps ->

    val product = productProps.product


    styledDiv {

        styledH1 {
            +product.name
        }

        fieldSet {

        }

    }


}