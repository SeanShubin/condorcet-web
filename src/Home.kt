import CondorcetEvents.LogoutRequest
import Html.div
import Html.header
import Html.link
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
