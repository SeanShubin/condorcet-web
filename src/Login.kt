import Events.LoginRequest
import Events.NavigateToRegisterRequest
import Html.button
import Html.div
import Html.header
import Html.input
import Html.link
import Html.password
import Html.span
import org.w3c.dom.events.KeyboardEvent

class Login: Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val loginModel = model.login
        val caption = header("Login")
        val error = if (loginModel.error == null) listOf() else listOf(span(loginModel.error, "error"))
        val nameOrEmail = input(text = loginModel.nameOrEmail, placeholder = "name or email")
        val password = password(text = loginModel.password, placeholder = "password")
        val handleLogin = {
            handleEvent(LoginRequest(nameOrEmail.value, password.value))
        }
        val loginButton = button(text = "Login", onclick = handleLogin)
        val handleKeypress: ((KeyboardEvent) -> dynamic) = { event ->
            if (event.keyCode == 13) {
                handleEvent(LoginRequest(nameOrEmail.value, password.value))
            }
        }
        nameOrEmail.onkeypress = handleKeypress
        password.onkeypress = handleKeypress
        val handleRegister = { handleEvent(NavigateToRegisterRequest )}
        val registerLink = link(text = "Register", onclick=handleRegister)
        val list = listOf(caption) + error + listOf(nameOrEmail, password, loginButton, registerLink)
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = nameOrEmail)
    }
}
