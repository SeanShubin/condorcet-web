package generic

import reactor.Reactor
import state.Model

class EventLoop(val reactor: Reactor,
                val environment: Environment){
    private var model:Model = Model.empty
    fun handleEvent(event:GenericEvent){
        val oldState = model
        val (newState, effects) = reactor.react(oldState, event)
        model = newState
        effects.forEach {
            it.apply(newState, environment, this::handleEvent)
        }
    }
}
