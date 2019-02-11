package reactor

import generic.Effect
import state.Model

data class Result(val model: Model, val effects:List<Effect>)
