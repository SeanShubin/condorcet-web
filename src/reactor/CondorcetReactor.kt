package reactor

import generic.GenericEvent
import state.Model
import event.CondorcetEvents.Initialize
import event.CondorcetEvents.LoginRequest
import event.CondorcetEvents.LoginSuccess
import event.CondorcetEvents.LoginFailure
import event.CondorcetEvents.RegisterRequest
import event.CondorcetEvents.RegisterSuccess
import event.CondorcetEvents.RegisterFailure
import event.CondorcetEvents.LogoutRequest
import event.CondorcetEvents.NavigateToLoginRequest
import event.CondorcetEvents.NavigateToRegisterRequest
import event.CondorcetEvents.NavigateToHomeRequest

class CondorcetReactor:Reactor{
    override fun react(model: Model, event: GenericEvent): Result {
        return when(event){
            is Initialize -> Result(model, listOf(NavigateToLoginRequest))
            is LoginRequest -> TODO("LoginRequest")
            is LoginSuccess -> TODO("LoginSuccess")
            is LoginFailure -> TODO("LoginFailure")
            is RegisterRequest -> TODO("RegisterRequest")
            is RegisterSuccess -> TODO("RegisterSuccess")
            is RegisterFailure -> TODO("RegisterFailure")
            is LogoutRequest -> TODO("LogoutRequest")
            is NavigateToRegisterRequest -> TODO("NavigateToRegisterRequest")
            is NavigateToLoginRequest -> TODO("NavigateToLoginRequest")
            is NavigateToHomeRequest -> TODO("NavigateToHomeRequest")
            else -> Result(model.copy(error = "Unsupported event: $event"), emptyList())
        }
    }
}
