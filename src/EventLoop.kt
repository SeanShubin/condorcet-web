class EventLoop(private val reactor: GenericReactor,
                private val api: Api,
                private val environment: Environment,
                private val components: GenericComponents) {
    fun handleEvent(event: GenericEvent) {
        console.log("handleEvent.event", event)
        val oldState = loadModel()
        console.log("handleEvent.oldState", oldState)
        val (newState, effects) = reactor.react(oldState, event)
        console.log("handleEvent.newState", newState)
        console.log("handleEvent.effects", effects)
        storeModel(newState)
        effects.forEach { effect ->
            try {
                console.log("handleEvent.effect", effect)
                effect.apply(newState, api, environment, components, this::handleEvent)
            } catch (ex: Throwable) {
                console.log("handleEvent.error-message:", ex.message)
                console.log("handleEvent.error-cause:", ex.cause)
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
