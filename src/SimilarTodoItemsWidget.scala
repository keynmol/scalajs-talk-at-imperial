import com.raquo.laminar.api.L.*

def SimilarTodoItemsWidget(
    state: Signal[AppState],
    draft: Signal[TodoDraft]
) =
  ul(
    cls := "grid grid-cols-1 gap-2",
    children <-- state
      .combineWith(draft)
      .mapN:
        case (AppState(index, search), TodoDraft(title, desc)) =>
          if title.trim.nonEmpty || desc.trim.nonEmpty then
            val combined = title + " " + desc

            val results =
              search
                .search(combined)
                .sortBy(_._2)
                .reverse
                .map(_._1)
                .flatMap(id => index.lookup(id).map(id -> _))
                .take(3)

            if results.isEmpty then Seq(li(i("no results..")))
            else
              results.map((id, item) =>
                val opacity =
                  if item.deleted then "opacity-50" else "opacity-100"
                li(
                  cls := "bg-white border-2 border-slate-700 rounded-md p-4",
                  a(
                    href := s"#todo-$id",
                    cls := "text-lg font-bold underline hover:no-underline",
                    cls := opacity,
                    item.title
                  )
                )
              )
          else Seq(li(i("start typing...")))
  )
