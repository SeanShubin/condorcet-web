import CondorcetEvents.LoginRequest
import CondorcetEvents.NavigateToRegisterRequest
import Html.button
import Html.div
import Html.header
import Html.input
import Html.link
import Html.password
import org.w3c.dom.HTMLElement

class Login: Renderable {
    override fun render(handleEvent: (GenericEvent) -> Unit): HTMLElement {
        val caption = header("Login")
        val nameOrEmail = input(placeholder = "name or email")
        val password = password(placeholder = "password")
        val handleLogin = { handleEvent(LoginRequest(nameOrEmail.value, password.value))}
        val loginButton = button(text = "Login", onclick = handleLogin)
        val handleRegister = { handleEvent(NavigateToRegisterRequest )}
        val registerLink = link(text = "Register", onclick=handleRegister)
        val list = listOf(caption, nameOrEmail, password, loginButton, registerLink)
        return div(contents = list, className = "single-column-flex")
    }
}
