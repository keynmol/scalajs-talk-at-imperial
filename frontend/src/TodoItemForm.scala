import com.raquo.laminar.api.L.*
import com.raquo.airstream.state.Var
import org.scalajs.dom.FormData

def TodoItemForm(
    state: Var[AppState],
    savedDescription: String = "",
    savedTitle: String = ""
) =
  val draft: Var[TodoDraft] = Var(SaveState.restoreDraft())
  val summary = TodoSummaryChart(state.signal)
  val similar = SimilarTodoItemsWidget(state.signal, draft.signal)

  val bus = EventBus[String]()

  div(
    cls := "grid grid-cols-2 gap-2",
    form(
      cls := "my-4 grid grid-cols-1 gap-2",
      action := "#",
      input(
        tpe := "text",
        placeholder := "title",
        cls := "text-xl p-2 rounded-md",
        onInput.mapToValue --> draft.updater[String]((cur, value) =>
          cur.copy(title = value)
        ),
        value <-- draft.signal.map(_.title)
      ),
      textArea(
        placeholder := "description",
        cls := "text-xl p-2 rounded-md",
        rows := 6,
        onInput.mapToValue --> draft.updater[String]((cur, value) =>
          cur.copy(description = value)
        ),
        value <-- draft.signal.map(_.description)
      ),
      button(
        "💫🪄 Use AI magic to fill title 🪄💫",
        cls := "bg-indigo-400 text-xl border-2 border-white p-4 text-white",
        onClick.preventDefault.mapTo(draft.now().description) --> bus.toObserver
      ),
      bus.events.flatMapSwitch { description =>
        val data = FormData()
        data.append("description", draft.now().description)

        FetchStream.post("/create-title", _.body(data))

      } --> draft.updater[String]((cur, value) => cur.copy(title = value)),
      onSubmit.mapToUnit --> { _ =>
        val itemTitle = draft.now().title.trim()
        val itemDescription = draft.now().description.trim()

        if itemTitle.nonEmpty then
          val item =
            TodoItem(title = itemTitle, description = itemDescription)

          state.update:
            case AppState(todoIdx, searchIdx) =>
              val (itemID, newIdx) = todoIdx.add(item)
              val newSearchIdx = searchIdx.add(itemID, item)

              AppState(newIdx, newSearchIdx)

          draft.set(TodoDraft("", ""))
      },
      button(
        tpe := "submit",
        "Create",
        cls := "bg-white text-xl border-2 border-slate-800 p-4"
      )
    ),
    div(
      cls := "my-4",
      child <-- draft.signal.map(draft =>
        if draft.isEmpty then summary
        else similar
      )
    )
  )
