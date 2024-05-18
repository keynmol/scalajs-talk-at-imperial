import sttp.openai.OpenAISyncClient
import sttp.openai.requests.completions.chat.ChatRequestBody.*
import sttp.openai.requests.completions.chat.message.*
import sttp.openai.requests.completions.chat.ChatRequestResponseData.ChatResponse

object MinimalApplication extends cask.MainRoutes {

  override def port: Int = 8080
  override def host: String = "0.0.0.0"

  @cask.get("/")
  def index() =
    cask.model.StaticResource(
      "index.html",
      getClass().getClassLoader(),
      Seq("Content-type" -> "text/html")
    )

  @cask.decorators.compress
  @cask.staticResources("/assets")
  def assets() = "assets"

  def handleOopsie(result: => String): String =
    try result
    catch
      case exc =>
        println(exc)
        "Something bad happened..."

  @cask.postForm("/create-title")
  def formEndpoint(description: String) = {
    handleOopsie(
      createTitle(description.take(2048)).choices
        .map(_.message.content)
        .headOption
        .getOrElse("")
    )
  }

  lazy val openAI = OpenAISyncClient(sys.env("OPENAI_KEY"))

  private def chatRequestBody(text: String) = ChatBody(
    model = ChatCompletionModel.CustomChatCompletionModel("gpt-3.5-turbo-0125"),
    messages = Seq(
      Message.UserMessage(
        content = Content.TextContent(
          s"Summarise this text in 1 short sentence, 5-10 words, as if it was a TODO item: $text"
        )
      )
    )
  )

  private def createTitle(description: String): ChatResponse =
    openAI.createChatCompletion(chatRequestBody(description))

  initialize()
}
