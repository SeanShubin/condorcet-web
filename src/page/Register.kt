package page

import event.CondorcetEvents.RegisterRequest
import event.CondorcetEvents.NavigateToLoginRequest
import generic.GenericEvent
import generic.Renderable
import html.Html.header
import html.Html.input
import html.Html.password
import html.Html.button
import html.Html.link
import html.Html.div
import org.w3c.dom.HTMLElement

class Register : Renderable {
    override fun render(handleEvent: (GenericEvent) -> Unit): HTMLElement {
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
