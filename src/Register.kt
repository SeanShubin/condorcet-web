import CondorcetEvents.NavigateToLoginRequest
import CondorcetEvents.RegisterRequest
import Html.button
import Html.div
import Html.header
import Html.input
import Html.link
import Html.password
import org.w3c.dom.HTMLElement

class Register : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): HTMLElement {
        val caption = header("Register")
        val name = input(placeholder = "name")
        val email = input(placeholder = "email")
        val password = password(placeholder = "password")
        val confirmPassword = password(placeholder = "confirm password")
        val handleRegister = { handleEvent(RegisterRequest(name.value, email.value, password.value, confirmPassword.value)) }
        val registerButton = button(text = "Register", onclick = handleRegister)
        val handleLogin = { handleEvent(NavigateToLoginRequest) }
        val loginLink = link(text = "Login", onclick = handleLogin)
        val list = listOf(caption, name, email, password, confirmPassword, registerButton, loginLink)
        return div(contents = list, className = "single-column-flex")
    }
}
