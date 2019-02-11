package generic

import state.Model

interface Effect {
    fun apply(model: Model, environment: Environment, handleEvent:(GenericEvent)->Unit)
}
