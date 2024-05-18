// val chart = Echarts.init(dom.document.getElementById("chart"))
// println(chart)

import com.raquo.laminar.api.L.*
import scalajs.js.Dynamic.literal as J
import scalajs.js.Array as A

def TodoSummaryChart(signal: Signal[AppState]) =
  var chartInstance = Option.empty[Echart]

  div(
    cls := "w-full h-full",
    onMountCallback(ctx =>
      chartInstance = Some(Echarts.init(ctx.thisNode.ref))
    ),
    signal --> { state =>
      val done = state.index.items.count(_._2.deleted)
      val notDone = state.index.items.length - done

      chartInstance.foreach: chart =>
        chart.setOption(
          J(
            title = J(text = "How you're doing"),
            xAxis = J(data = A("done", "not done")),
            series = A(
              J(data = A(done, notDone), name = "count", `type` = "bar")
            ),
            yAxis = J()
          )
        )

    }
  )
