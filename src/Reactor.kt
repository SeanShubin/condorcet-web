interface Reactor {
    fun react(model: Model, event: GenericEvent):Result
}
