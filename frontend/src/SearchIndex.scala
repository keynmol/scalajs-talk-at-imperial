import com.raquo.laminar.api.L.*

case class SearchIndex(
    inverted: Map[String, Map[Int, Float]],
    totalCount: Int
):
  def add(id: Int, item: TodoItem): SearchIndex =
    val body =
      Tokenizer.run(item.description).groupMapReduce(identity)(_ => 1.0f)(_ + _)

    val title =
      Tokenizer.run(item.title).groupMapReduce(identity)(_ => 1.0f)(_ + _)

    val titleMultiplier = 1.2f

    val newEntry =
      (body.keySet ++ title.keySet).foldLeft(inverted):
        case (inverted, token) =>
          val documentWeight =
            title.getOrElse(token, 0.0f) * titleMultiplier +
              body.getOrElse(token, 0.0f)

          inverted.updatedWith(token):
            case None => Some(Map(id -> documentWeight))
            case Some(existing) =>
              Some(existing.updated(id, documentWeight))

    copy(totalCount = totalCount + 1, inverted = newEntry)
  end add

  def search(query: String): List[(Int, Float)] =
    val queryTokens = Tokenizer.run(query)
    val resolvedTokens: Map[String, Map[Int, Float]] =
      queryTokens.flatMap(tok => inverted.get(tok).map(tok -> _)).toMap

    val documentScores =
      collection.mutable.Map.empty[Int, Float].withDefaultValue(0.0)
    // val documentLengths =
    //   collection.mutable.Map.empty[Int, Int].withDefaultValue(0)

    resolvedTokens.foreach:
      case (token, occurrences) =>
        idf.get(token) match
          case None =>
          case Some(idf) =>
            occurrences.foreach: (documentId, weightInDocument) =>
              documentScores.update(
                documentId,
                documentScores(documentId) + weightInDocument * idf
              )

    documentScores.toList

  private lazy val idf: Map[String, Float] =
    inverted.map((token, occurrences) =>
      token -> totalCount.toFloat / occurrences.size.toFloat
    )

object SearchIndex:
  def create(todo: TodoIndex) =
    todo.items.foldLeft(SearchIndex(Map.empty, 0)):
      case (si, (id, item)) => si.add(id, item)
