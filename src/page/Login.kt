package page

import event.CondorcetEvents.LoginRequest
import event.CondorcetEvents.NavigateToRegisterRequest
import generic.GenericEvent
import generic.Renderable
import html.Html.header
import html.Html.input
import html.Html.password
import html.Html.button
import html.Html.link
import html.Html.div
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
