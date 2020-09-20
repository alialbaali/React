import counter.Counter
import kotlinx.browser.document
import kotlinx.browser.window
import react.child
import react.dom.render

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            child(Counter) {
                attrs {
                    count = 0
                }
            }
        }
    }
}