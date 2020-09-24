package counter

import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.css.properties.Timing
import kotlinx.css.properties.border
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import kotlinx.html.js.onClickFunction
import react.RProps
import react.RState
import react.functionalComponent
import react.useState
import styled.*

external interface CounterProps : RProps {
    var count: Int
}

data class CounterState(val count: Int) : RState

val Counter = functionalComponent<CounterProps> { props ->

    styledDiv {

        css { +CounterStyle.root }
        val (state, setState) = useState(CounterState(props.count))

        styledH1 {
            css {
                color = Color.white
                fontSize = 8.em
            }
            +state.count.toString()
        }

        styledDiv {
            css { +CounterStyle.op }
            styledButton {
                +"-"
                attrs {
                    onClickFunction = {
                        setState(state.copy(count = state.count.minus(1)))
                    }
                }
                css {
                    +CounterStyle.button
                    border(5.px, BorderStyle.dashed, Color.red, 12.px)
                    backgroundColor = Color.transparent
                    hover {
                        backgroundColor = Color.red
                    }
                }
            }

            styledButton {
                +"+"
                attrs {
                    onClickFunction = {
                        setState(state.copy(count = state.count.plus(1)))
                    }
                }
                css {
                    border(5.px, BorderStyle.dashed, Color.blue, 12.px)
                    +CounterStyle.button
                    hover {
                        backgroundColor = Color.blue
                    }
                }
            }
        }
        if (state.count == 10)
            window.alert("Good Job")
    }
}

object CounterStyle : StyleSheet("counter.getCounter.counter.CounterStyle", isStatic = true) {

    val root by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.center
        alignItems = Align.center
    }

    val button by css {
        minWidth = 150.px
        minHeight = 75.px
        margin(16.px)
        fontSize = 6.em
        hover {
            transition(duration = 300.ms, timing = Timing.linear)
            borderColor = Color.transparent
        }
    }

    val op by css {
        display = Display.flex
        flexDirection = FlexDirection.row
    }
}