import kotlinx.browser.document
import kotlinx.browser.window
import react.dom.render
import todo.TodoApp

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            TodoApp {

            }
        }
    }
}