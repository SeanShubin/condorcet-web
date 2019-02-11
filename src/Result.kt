import kotlin.js.Promise

data class Result(val model: Model, val effects: List<Promise<Effect>>)
