import com.raquo.laminar.api.L.*

def TodoItemList(
    state: Var[AppState]
) =
  val actionUpdate =
    (id: Int, act: Action) =>
      state.update: state =>
        act match
          case Action.Delete =>
            state.copy(index = state.index.delete(id))
          case Action.Restore =>
            state.copy(index = state.index.restore(id))

  div(
    cls := "grid gap-4 grid-cols-1",
    children <-- state.signal
      .map(_.index)
      .map: todo =>
        todo.items
          .sortBy(_._1)
          .reverse
          .map(
            TodoItemCard(_, _, actionUpdate)
          )
  )

def TodoItemCard(id: Int, item: TodoItem, callback: (Int, Action) => Unit) =
  val btn =
    if item.deleted then
      button(
        "Reopen",
        cls := "m-2 text-md bg-amber-200 p-2 border-1 border-slate-700",
        onClick --> { _ => callback(id, Action.Restore) }
      )
    else
      button(
        "Done",
        cls := "m-2 text-md bg-lime-200 p-2 border-1 border-slate-700",
        onClick --> { _ => callback(id, Action.Delete) }
      )

  div(
    idAttr := s"todo-$id",
    cls := "bg-white rounded-md border-slate-300 p-4 border-1",
    cls := (if item.deleted then "opacity-50" else "opacity-100"),
    p(btn, span(item.title, cls := "text-4xl"), cls := "align-middle"),
    p(
      cls := "max-h-24 text-ellipsis overflow-hidden",
      item.description
    )
  )

enum Action:
  case Delete, Restore
