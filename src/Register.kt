import CondorcetEvents.NavigateToLoginRequest
import CondorcetEvents.RegisterRequest
import Html.button
import Html.div
import Html.header
import Html.input
import Html.link
import Html.password
import Html.span

class Register : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val registerModel = model.register
        val caption = header("Register")
        val error = if (registerModel.error == null) listOf() else listOf(span(registerModel.error, "error"))
        val name = input(text = registerModel.name, placeholder = "name")
        val email = input(text = registerModel.email, placeholder = "email")
        val password = password(text = registerModel.password, placeholder = "password")
        val confirmPassword = password(text = registerModel.confirmPassword, placeholder = "confirm password")
        val handleRegister = { handleEvent(RegisterRequest(name.value, email.value, password.value, confirmPassword.value)) }
        val registerButton = button(text = "Register", onclick = handleRegister)
        val handleLogin = { handleEvent(NavigateToLoginRequest) }
        val loginLink = link(text = "Login", onclick = handleLogin)
        val list = listOf(caption) + error + listOf(name, email, password, confirmPassword, registerButton, loginLink)
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = name)
    }
}
