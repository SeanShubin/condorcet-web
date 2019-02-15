import CondorcetEffects.FireEvent
import CondorcetEffects.Render
import CondorcetEvents.Initialize
import CondorcetEvents.LoginFailure
import CondorcetEvents.LoginRequest
import CondorcetEvents.LoginSuccess
import CondorcetEvents.LogoutRequest
import CondorcetEvents.NavigateToDebugRequest
import CondorcetEvents.NavigateToElectionsRequest
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
            is Initialize -> initialize(model)
            is LoginRequest -> loginRequest(model, event)
            is LoginSuccess -> loginSuccess(model, event)
            is LoginFailure -> loginFailure(model, event)
            is RegisterRequest -> registerRequest(model, event)
            is RegisterSuccess -> registerSuccess(model, event)
            is RegisterFailure -> registerFailure(model, event)
            is LogoutRequest -> logoutRequest(model)
            is NavigateToRegisterRequest -> navigateToRegisterRequest(model)
            is NavigateToLoginRequest -> navigateToLoginRequest(model)
            is NavigateToHomeRequest -> navigateToHomeRequest(model)
            is NavigateToDebugRequest -> navigateToDebugRequest(model)
            is NavigateToElectionsRequest -> navigateToElectionsRequest(model)
            else -> unsupportedEvent(model, event)
        }
        console.log("react.result.mode", result.model)
        console.log("react.result.effects", result.effects)
        return result
    }

    private fun initialize(model: Model): Result =
            Result(model, render())

    private fun loginRequest(model: Model, event: LoginRequest): Result {
        val promise = api.login(event.nameOrEmail, event.password)
        val effect = promise.then { loginResponse ->
            FireEvent(LoginSuccess(loginResponse.name))
        }.catch { throwable ->
            FireEvent(LoginFailure(throwable.message))
        }
        val newLogin = model.login.copy(nameOrEmail = event.nameOrEmail, password = event.password)
        return Result(model.copy(login = newLogin), listOf(effect))
    }

    private fun loginSuccess(model: Model, event: LoginSuccess): Result =
            Result(model.purgePasswords().withHome(name = event.name), render())

    private fun loginFailure(model: Model, event: LoginFailure): Result =
            Result(model.withLoginError(event.reason), render())

    private fun registerRequest(model: Model, event: RegisterRequest): Result {
        val newRegisterModel = model.register.copy(
                name = event.name,
                email = event.email,
                password = event.password,
                confirmPassword = event.confirmPassword)
        val newModel = model.copy(register = newRegisterModel)
        return if (event.password == event.confirmPassword) {
            val promise = api.register(event.name, event.email, event.password)
            val effect = promise.then { registerResponse ->
                FireEvent(RegisterSuccess(registerResponse.name))
            }.catch { throwable ->
                FireEvent(RegisterFailure(throwable.message))
            }
            Result(newModel, listOf(effect))
        } else {
            val effect = FireEvent(RegisterFailure("Password does not match confirmation password"))
            Result(newModel, listOf(Promise.resolve(effect)))
        }
    }

    private fun registerSuccess(model: Model, event: RegisterSuccess) =
            Result(model.purgePasswords().withHome(name = event.name), render())

    private fun logoutRequest(model: Model) = Result(model.purgePasswords().withLogin(), render())

    private fun registerFailure(model: Model, event: RegisterFailure) = Result(model.withRegisterError(event.reason), render())

    private fun navigateToLoginRequest(model: Model) = Result(model.withLogin(), render())

    private fun navigateToRegisterRequest(model: Model) = Result(model.withRegister(), render())

    private fun navigateToHomeRequest(model: Model) = Result(model.withHome(), render())

    private fun navigateToDebugRequest(model: Model) = Result(model.withDebug(), render())

    private fun navigateToElectionsRequest(model: Model): Result {
        val newModel = model.withElections()
        val renderEffect = Promise.resolve(Render)
        val credential = Api.Credential(model.credential.name, model.credential.password)
        val getElectionsEffect: Promise<FireEvent> = api.listElections(credential).then { listElectionsResponse ->
            FireEvent(CondorcetEvents.GetElectionsSuccess(listElectionsResponse.elections))
        }.catch { throwable ->
            FireEvent(CondorcetEvents.GetElectionsFailure(throwable.message))
        }
        return Result(newModel, listOf(renderEffect, getElectionsEffect))
    }
    private fun unsupportedEvent(model: Model, event: GenericEvent) =
            Result(model.withUnsupportedError(error = "Unsupported event: $event"), render())

    private fun render(): List<Promise<Effect>> = listOf(Promise.resolve(Render))
}
