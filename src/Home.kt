import CondorcetEvents.LogoutRequest
import CondorcetEvents.NavigateToElectionsRequest
import CondorcetEvents.NavigateToVotesRequest
import Html.div
import Html.header
import Html.link
import Html.span

class Home : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val homeModel = model.home
        val caption = header("Home, ${homeModel.name}")
        val error = if (homeModel.error == null) listOf() else listOf(span(homeModel.error, "error"))
        val electionsLink = link("Elections") {
            handleEvent(NavigateToElectionsRequest)
        }
        val votesLink = link("Votes") {
            handleEvent(NavigateToVotesRequest)
        }
        val logoutLink = link(text = "Logout") {
            handleEvent(LogoutRequest)
        }
        val list = listOf(caption) + error + listOf(electionsLink, votesLink, logoutLink)
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = null)
    }
}
