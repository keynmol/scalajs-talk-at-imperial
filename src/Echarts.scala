import scalajs.js, js.annotation.*
import org.scalajs.dom.Element

@js.native
@JSImport("echarts", JSImport.Namespace)
object Echarts extends js.Object:
  def init(element: Element): Echart = js.native

@js.native
trait Echart extends js.Object {
  def setOption(opts: js.Any): Unit = js.native
}
