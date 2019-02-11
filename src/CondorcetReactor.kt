import CondorcetEffects.FireEvent
import CondorcetEffects.Render
import CondorcetEvents.Initialize
import CondorcetEvents.LoginFailure
import CondorcetEvents.LoginRequest
import CondorcetEvents.LoginSuccess
import CondorcetEvents.LogoutRequest
import CondorcetEvents.NavigateToHomeRequest
import CondorcetEvents.NavigateToLoginRequest
import CondorcetEvents.NavigateToRegisterRequest
import CondorcetEvents.RegisterFailure
import CondorcetEvents.RegisterRequest
import CondorcetEvents.RegisterSuccess

class CondorcetReactor:Reactor{
    override fun react(model: Model, event: GenericEvent): Result {
        return when(event){
            is Initialize -> Result(model, listOf(FireEvent(NavigateToLoginRequest)))
            is LoginRequest -> TODO("LoginRequest")
            is LoginSuccess -> TODO("LoginSuccess")
            is LoginFailure -> TODO("LoginFailure")
            is RegisterRequest -> TODO("RegisterRequest")
            is RegisterSuccess -> TODO("RegisterSuccess")
            is RegisterFailure -> TODO("RegisterFailure")
            is LogoutRequest -> TODO("LogoutRequest")
            is NavigateToRegisterRequest -> Result(model.copy(page = "register"), listOf(Render))
            is NavigateToLoginRequest -> Result(model.copy(page = "login"), listOf(Render))
            is NavigateToHomeRequest -> Result(model.copy(page = "home"), listOf(Render))
            else -> Result(model.copy(error = "Unsupported event: $event"), emptyList())
        }
    }
}
