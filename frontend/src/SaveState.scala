import com.raquo.laminar.api.L.*
import org.scalajs.dom
import scala.util.Try

import upickle.default.*

object SaveState:
  private val INDEX_KEY = "hello-scalajs"
  private val DRAFT_KEY = "hello-scalajs-draft"

  private def readFrom[T: ReadWriter](key: String, default: T) =
    dom.window.localStorage.getItem(key) match
      case null => default
      case other =>
        Try(read[T](other)).getOrElse(default)

  private def save[T: ReadWriter](key: String, signal: Signal[T]) =
    signal.map(upickle.default.write(_)) --> { written =>
      dom.window.localStorage.setItem(key, written)
    }

  def restoreIndex(): TodoIndex =
    readFrom[TodoIndex]("hello-scalajs", TodoIndex.empty)

  def restoreDraft(): TodoDraft =
    readFrom[TodoDraft]("hello-scalajs-draft", TodoDraft("", ""))

  def saveIndex(state: Signal[TodoIndex]) =
    save(INDEX_KEY, state)

  def saveDraft(state: Signal[TodoDraft]) =
    save(DRAFT_KEY, state)
