import upickle.default.*

case class TodoDraft(title: String, description: String) derives ReadWriter:
  def isEmpty = title.trim.isEmpty() && description.trim().isEmpty()
