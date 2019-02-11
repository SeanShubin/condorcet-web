import CondorcetEvents.LoginRequest
import CondorcetEvents.NavigateToRegisterRequest
import Html.button
import Html.div
import Html.header
import Html.input
import Html.link
import Html.password
import Html.span
import org.w3c.dom.HTMLElement

class Login: Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): HTMLElement {
        val loginModel = model.login
        val caption = header("Login")
        val error = if (loginModel.error == null) listOf() else listOf(span(loginModel.error, "error"))
        val nameOrEmail = input(placeholder = "name or email")
        val password = password(placeholder = "password")
        val handleLogin = {
            console.log("handleLogin")
            handleEvent(LoginRequest(nameOrEmail.value, password.value))
        }
        val loginButton = button(text = "Login", onclick = handleLogin)
        val handleRegister = { handleEvent(NavigateToRegisterRequest )}
        val registerLink = link(text = "Register", onclick=handleRegister)
        val list = listOf(caption) + error + listOf(nameOrEmail, password, loginButton, registerLink)
        return div(contents = list, className = "single-column-flex")
    }
}
