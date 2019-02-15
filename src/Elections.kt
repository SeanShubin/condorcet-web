import CondorcetEvents.LogoutRequest
import CondorcetEvents.NavigateToHomeRequest
import Html.div
import Html.header
import Html.link
import Html.span

class Elections : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val electionsModel = model.elections
        val caption = header("Elections")
        val error = if (electionsModel.error == null) listOf() else listOf(span(electionsModel.error, "error"))
        val homeLink = link(text = "Home") {
            handleEvent(NavigateToHomeRequest)
        }
        val logoutLink = link(text = "Logout") {
            handleEvent(LogoutRequest)
        }
        val list = listOf(caption) + error + listOf(homeLink, logoutLink)
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = null)
    }
}
