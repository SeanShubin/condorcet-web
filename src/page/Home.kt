package page

import event.CondorcetEvents.LogoutRequest
import generic.GenericEvent
import generic.Renderable
import html.Html.div
import html.Html.header
import html.Html.link
import org.w3c.dom.HTMLElement

class Home : Renderable {
    override fun render(handleEvent: (GenericEvent) -> Unit): HTMLElement {
        val caption = header("Condorcet")
        val handleLogout = { handleEvent(LogoutRequest) }
        val logoutLink = link(text = "Register", onclick = handleLogout)
        val list = listOf(caption, logoutLink)
        return div(contents = list, className = "single-column-flex")
    }
}
