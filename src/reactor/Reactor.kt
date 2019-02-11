package reactor

import generic.GenericEvent
import state.Model

interface Reactor {
    fun react(model: Model, event: GenericEvent):Result
}
