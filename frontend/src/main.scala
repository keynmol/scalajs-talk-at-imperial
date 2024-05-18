import com.raquo.laminar.api.L.*
import org.scalajs.dom

@main def hello =
  val indexInit = SaveState.restoreIndex()
  val state = Var(AppState(indexInit, SearchIndex.create(indexInit)))

  val app =
    div(
      SaveState.saveIndex(state.signal.map(_._1)),
      p("TODO app with Scala.js", cls := "text-6xl"),
      p(
        "This is not a simple TODO app - as you type out your new item, a list of similar "
          + "TODO items will appear on the right",
        cls := "text-sm mx-2"
      ),
      TodoItemForm(state, "", ""),
      TodoItemList(state)
    )

  renderOnDomContentLoaded(
    org.scalajs.dom.document.getElementById("hello"),
    app
  )
