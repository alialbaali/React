package todo

data class TodoItem(
    val id: Int,
    val task: String,
    val isChecked: Boolean = false,
)