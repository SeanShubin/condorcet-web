data class Result(val model: Model, val effects: List<Effect>) {
    constructor(model: Model, vararg effects: Effect) : this(model, effects.toList())
}
