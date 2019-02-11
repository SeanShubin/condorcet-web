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
import kotlin.js.Promise

class CondorcetReactor(private val api: Api) : Reactor {
    override fun react(model: Model, event: GenericEvent): Result {
        console.log("react.model", model)
        console.log("react.event", event)
        val result = when (event) {
            is Initialize -> Result(model, listOf(Promise.resolve(FireEvent(NavigateToLoginRequest))))
            is LoginRequest -> loginRequest(model, event)
            is LoginSuccess -> Result(
                    model.copy(page = "home", home = Model.Companion.HomeModel(name = event.name)),
                    listOf(Promise.resolve(Render)))
            is LoginFailure -> Result(model.copy(error = event.reason), render())
            is RegisterRequest -> TODO("RegisterRequest")
            is RegisterSuccess -> TODO("RegisterSuccess")
            is RegisterFailure -> TODO("RegisterFailure")
            is LogoutRequest -> TODO("LogoutRequest")
            is NavigateToRegisterRequest -> Result(model.copy(page = "register"), render())
            is NavigateToLoginRequest -> Result(model.copy(page = "login"), render())
            is NavigateToHomeRequest -> Result(model.copy(page = "home"), render())
            else -> Result(model.copy(error = "Unsupported event: $event"), emptyList())
        }
        console.log("react.result.mode", result.model)
        console.log("react.result.effects", result.effects)
        return result
    }

    private fun loginRequest(model: Model, event: LoginRequest): Result {
        val promise = api.login(event.nameOrEmail, event.password)
        val effect = promise.then { loginResponse ->
            FireEvent(LoginSuccess(loginResponse.name))
        }.catch { throwable ->
            FireEvent(LoginFailure(throwable.message))
        }
        return Result(model, listOf(effect))
    }

    private fun render(): List<Promise<Effect>> = listOf(Promise.resolve(Render))
}
