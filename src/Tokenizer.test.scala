import munit.*

class TokenizerSpec extends munit.FunSuite:
  test("Stemming") {
    assertEquals(
      Tokenizer.run(
        "Thus, HVM2 Nets contain only a single free main port. " +
          "Wirings are possible between trees through pairs of VAR nodes with the same names. " +
          "Note, however, that a variable can only occur twice"
      ),
      List(
        "hvm2",
        "net",
        "single",
        "free",
        "main",
        "port",
        "wiring",
        "tree",
        "pair",
        "var",
        "node",
        "name",
        "note",
        "variable",
        "occur"
      )
    )
  }
