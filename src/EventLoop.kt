class EventLoop(private val reactor: Reactor,
                private val environment: Environment,
                private val components: Components) {
    private var model: Model = Model.empty
    fun handleEvent(event: GenericEvent) {
        val oldState = model
        val (newState, effects) = reactor.react(oldState, event)
        model = newState
        effects.forEach { promise ->
            promise.then { effect ->
                effect.apply(newState, environment, components, this::handleEvent)
            }
        }
    }
}
