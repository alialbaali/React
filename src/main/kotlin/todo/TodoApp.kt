package todo

import fc
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import styled.*

data class TodoAppState(val todoItems: List<TodoItem>)

val TodoApp = fc<RProps> {

    styledDiv {

        css { +TodoAppStyle.Root }

        var todoAppState by useState { TodoAppState(emptyList()) }

        fun getId() = todoAppState.todoItems.size

        NavBarComponent {
            numOfTodoItems = todoAppState.todoItems.count()
        }

        AddTodoItemComponent {
            onAdd = { task ->
                val todoItems = todoAppState.todoItems
                val todoItem = TodoItem(getId(), task)
                todoAppState = TodoAppState(todoItems.plus(todoItem))
            }
        }

        todoAppState.todoItems.forEach { todoItem ->
            TodoItemComponent {
                this.key = todoItem.id.toString()
                this.todoItem = todoItem
                onCheck = { id, isChecked ->
                    val todoItems = todoAppState.todoItems
                        .toMutableList()

                    val todoItem = todoItems.find { it.id == id }!!

                    todoAppState = TodoAppState(todoItems.apply {
                        remove(todoItem)
                        add(todoItem.copy(isChecked = isChecked))
                    })
                }
                onEdit = { id, task ->
                    val todoItems = todoAppState.todoItems
                        .toMutableList()
                    val todoItem = todoItems.find { it.id == id }!!
                    todoAppState = TodoAppState(todoItems.apply {
                        remove(todoItem)
                        add(todoItem.copy(task = task))
                    })
                }
                onDelete = { id ->
                    val todoItems = todoAppState.todoItems
                    todoAppState = TodoAppState(todoItems.minus(todoItems.first { it.id == id }))
                }
            }
        }
    }
}

external interface NavBarProps : RProps {
    var numOfTodoItems: Int
}

val NavBarComponent = fc<NavBarProps> { props ->
    styledH1 {
        css {
            color = Color.blue
        }
        +"Number of tasks: ${props.numOfTodoItems}"
    }
}

external interface AddItemProps : RProps {
    var onAdd: (task: String) -> Unit
}

val AddTodoItemComponent = fc<AddItemProps> { props ->

    var task by useState { "" }
    var isError by useState { false }

    styledDiv {

        css {
            display = Display.flex
            justifyContent = JustifyContent.flexStart
            alignContent = Align.flexStart
        }

        styledDiv {

            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                justifyContent = JustifyContent.center
                alignContent = Align.center
            }

            styledInput(InputType.text) {
                attrs {
                    value = task
                    placeholder = "Type a task"
                    onChangeFunction = { event ->
                        val value = (event.target as HTMLInputElement).value
                        task = value
                    }
                }
                css {
                    focus {
                        padding(3.px)
                        border(3.px, BorderStyle.solid, Color.blue, 8.px)
                    }
                }
            }

            styledH6 {
                css {
                    color = Color.red
                }
                +if (isError) "Task can't be empty" else ""
            }
        }

        styledButton {
            +"Add"

            css {
                +TodoAppStyle.AddButton
            }
            attrs {
                onClickFunction = {
                    if (task.isNotBlank())
                        props.onAdd(task).also { task = "" }
                    else
                        isError = true
                }
            }
        }
    }

}


external interface TodoItemProps : RProps {
    var todoItem: TodoItem
    var onCheck: (id: Int, isChecked: Boolean) -> Unit
    var onEdit: (id: Int, task: String) -> Unit
    var onDelete: (id: Int) -> Unit
}

val TodoItemComponent = fc<TodoItemProps> { props ->

    var task by useState { props.todoItem.task }
    var isEdit by useState { false }

    styledDiv {

        css { +TodoAppStyle.TodoItem }

        styledH5 {
            css {
                fontSize = 2.em
                margin(12.px)
            }
            +props.todoItem.id.toString()
        }

        styledInput(InputType.checkBox) {
            css { +TodoAppStyle.CheckButton }
            attrs {
                onChangeFunction = { event ->
                    val isChecked = (event.target as HTMLInputElement).checked
                    props.onCheck(props.todoItem.id, isChecked)
                }
            }
        }

        if (isEdit) {

            styledInput(InputType.text) {
                attrs {
                    value = task
                    onChangeFunction = { event ->
                        task = (event.target as HTMLInputElement).value
                    }
                }
            }

            styledButton {
                +"Done"
                attrs {
                    onClickFunction = { event ->
                        if (task.isNotBlank())
                            props.onEdit(props.todoItem.id, task).also { isEdit = false }
                    }
                }
            }
        }

        if (!isEdit) {

            styledH3 {
                css { +TodoAppStyle.Task }
                +props.todoItem.task
            }

            styledButton {
                +"Edit"
                css {
                    +TodoAppStyle.EditButton
                }
                attrs {
                    onClickFunction = {
                        isEdit = true
                    }
                }
            }

            styledButton {
                +"Delete"
                css {
                    +TodoAppStyle.DeleteButton
                }
                attrs {
                    onClickFunction = {
                        props.onDelete(props.todoItem.id)
                    }
                }
            }
        }
    }


}

object TodoAppStyle : StyleSheet("TodoAppStyleSheet", isStatic = true) {

    val Root by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.spaceEvenly
        alignItems = Align.center
        fontFamily = "sans-serif"
    }

    val TodoItem by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.spaceEvenly
        alignItems = Align.center
        flexWrap = FlexWrap.wrap
    }

    val Task by css {
        fontSize = 3.em
        fontWeight = FontWeight.w600
    }

    val AddButton by css {
        margin(12.px)
    }

    val CheckButton by css {
        minHeight = 25.px
        minWidth = 25.px
        margin(12.px)
    }

    val DeleteButton by css {
        padding(16.px)
        backgroundColor = Color.red
        color = Color.white
        fontSize = 1.25.em
        margin(12.px)
        border(0.px, BorderStyle.none, Color.transparent, 8.px)
    }

    val EditButton by css {
        padding(16.px)
        fontSize = 1.25.em
        backgroundColor = Color.slateGray
        color = Color.white
        margin(12.px)
        border(0.px, BorderStyle.none, Color.transparent, 8.px)
    }

}