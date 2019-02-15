class EventLoop(private val reactor: Reactor,
                private val environment: Environment,
                private val components: Components) {
    private var model: Model = Model.empty
    fun handleEvent(event: GenericEvent) {
        console.log("handleEvent.event", event)
        val oldState = model
        console.log("handleEvent.oldState", oldState)
        val (newState, effects) = reactor.react(oldState, event)
        console.log("handleEvent.newState", newState)
        console.log("handleEvent.effects", effects)
        model = newState
        effects.forEach { promise ->
            promise.then { effect ->
                effect.apply(newState, environment, components, this::handleEvent)
            }.catch { throwable ->
                console.log(throwable.message)
                console.log(throwable.cause)
            }
        }
    }
}
