import upickle.default.{ReadWriter}

case class TodoItem(
    title: String,
    description: String,
    deleted: Boolean = false
) derives ReadWriter
