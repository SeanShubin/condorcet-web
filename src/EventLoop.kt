class EventLoop(private val reactor: Reactor,
                private val environment: Environment,
                private val components: Components) {
    fun handleEvent(event: GenericEvent) {
        console.log("handleEvent.event", event)
        val oldState = loadModel()
        console.log("handleEvent.oldState", oldState)
        val (newState, effects) = reactor.react(oldState, event)
        console.log("handleEvent.newState", newState)
        console.log("handleEvent.effects", effects)
        storeModel(newState)
        effects.forEach { promise ->
            promise.then { effect ->
                console.log("handleEvent.effect", effect)
                effect.apply(newState, environment, components, this::handleEvent)
            }.catch { throwable ->
                console.log("error message:", throwable.message)
                console.log("error cause:", throwable.cause)
            }
        }
    }

    private fun loadModel(): Model {
        val modelString = environment.window.sessionStorage.getItem("model")
        return if (modelString == null) {
            Model.empty
        } else {
            return Model.fromString(modelString)
        }
    }

    private fun storeModel(model: Model) {
        val modelString = JSON.stringify(model, null, 2)
        environment.window.sessionStorage.setItem("model", modelString)
    }
}
