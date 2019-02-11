import CondorcetEvents.LogoutRequest
import Html.div
import Html.header
import Html.link
import Html.span

class Home : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val homeModel = model.home
        val caption = header("Home, ${homeModel.name}")
        val error = if (homeModel.error == null) listOf() else listOf(span(homeModel.error, "error"))
        val handleLogout = { handleEvent(LogoutRequest) }
        val logoutLink = link(text = "Logout", onclick = handleLogout)
        val list = listOf(caption) + error + listOf(logoutLink)
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = null)
    }
}
