package effect

import generic.Effect
import generic.Environment
import generic.GenericEvent
import state.Model

object CondorcetEffects{
    object Render: Effect{
        override fun apply(model: Model, environment: Environment, handleEvent: (GenericEvent) -> Unit){
            environment.renderer.render(model.page)
        }
    }
}