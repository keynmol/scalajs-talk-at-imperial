import com.raquo.laminar.api.L.*
import upickle.default.ReadWriter

case class TodoIndex private (
    private val todos: Map[Int, TodoItem],
    private val lastId: Int
) derives ReadWriter:
  def add(item: TodoItem): (Int, TodoIndex) =
    val newId = lastId + 1
    newId -> copy(todos = todos.updated(newId, item), lastId = newId)

  def lookup(id: Int): Option[TodoItem] =
    todos.get(id)

  def delete(id: Int) =
    copy(todos.updatedWith(id)(_.map(_.copy(deleted = true))))

  def restore(id: Int) =
    copy(todos.updatedWith(id)(_.map(_.copy(deleted = false))))

  def items = todos.toList

object TodoIndex:
  def empty = TodoIndex(Map.empty, 1000)
