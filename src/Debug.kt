import Html.div
import Html.header
import Html.link
import Html.pre
import Html.span

class Debug : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val debugModel = model.debug
        val caption = header("Debug")
        val error = if (debugModel.error == null) listOf() else listOf(span(debugModel.error, "error"))
        val modelString = pre(JSON.stringify(model, null, 2))
        val homeLink = link("Home") {
            handleEvent(CondorcetEvents.NavigateToHomeRequest)
        }
        val loginLink = link("Login") {
            handleEvent(CondorcetEvents.NavigateToLoginRequest)
        }
        val registerLink = link("Register") {
            handleEvent(CondorcetEvents.NavigateToRegisterRequest)
        }
        val electionsLink = link("Elections") {
            handleEvent(CondorcetEvents.NavigateToElectionsRequest)
        }
        val votesLink = link("Votes") {
            handleEvent(CondorcetEvents.NavigateToVotesRequest)
        }
        val logoutLink = link(text = "Logout") {
            handleEvent(CondorcetEvents.LogoutRequest)
        }

        val list = listOf(caption) + error + listOf(modelString, homeLink, loginLink, registerLink, electionsLink, votesLink, logoutLink)
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = null)
    }
}
