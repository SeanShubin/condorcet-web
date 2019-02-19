interface GenericReactor {
    fun react(model: Model, event: GenericEvent): Result
}
