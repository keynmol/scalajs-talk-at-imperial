import upickle.default.*

case class TodoDraft(title: String, description: String) derives ReadWriter
