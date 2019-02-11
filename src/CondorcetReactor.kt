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
import CondorcetEvents.SetError
import kotlin.js.Promise

class CondorcetReactor(private val api: Api) : Reactor {
    override fun react(model: Model, event: GenericEvent): Result {
        return when(event){
            is Initialize -> Result(model, listOf(Promise.resolve(FireEvent(NavigateToLoginRequest))))
            is LoginRequest -> loginRequest(model, event)
            is LoginSuccess -> Result(
                    model.copy(page = "home", home = Model.Companion.HomeModel(name = event.name)),
                    listOf(Promise.resolve(Render)))
            is LoginFailure -> TODO("LoginFailure")
            is RegisterRequest -> TODO("RegisterRequest")
            is RegisterSuccess -> TODO("RegisterSuccess")
            is RegisterFailure -> TODO("RegisterFailure")
            is LogoutRequest -> TODO("LogoutRequest")
            is NavigateToRegisterRequest -> Result(model.copy(page = "register"), listOf(Promise.resolve(Render)))
            is NavigateToLoginRequest -> Result(model.copy(page = "login"), listOf(Promise.resolve(Render)))
            is NavigateToHomeRequest -> Result(model.copy(page = "home"), listOf(Promise.resolve(Render)))
            else -> Result(model.copy(error = "Unsupported event: $event"), emptyList())
        }
    }

    private fun loginRequest(model: Model, event: LoginRequest): Result {
        val promise = api.login(event.nameOrEmail, event.password)
        val effect = promise.then { loginResponse ->
            FireEvent(LoginSuccess(loginResponse.name))
        }.catch { throwable ->
            FireEvent(SetError(throwable.message))
        }
        return Result(model, listOf(effect))
    }
}
