import Html.div
import Html.header
import Html.link
import Html.span
import org.w3c.dom.HTMLElement

class ElectionPage : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val electionModel = model.electionModel
        val election = electionModel.election
        val caption = header("Election")
        val error = if (electionModel.error == null) listOf() else listOf(span(electionModel.error, "error"))
        val owner = labelText("owner", election.owner)
        val name = labelText("name", election.name)
        val start = labelText("start", election.start)
        val end = labelText("end", election.end)
        val secretBallot = labelText("secretBallot", election.secretBallot)
        val status = labelText("status", election.status)
        val grid = div(owner + name + start + end + secretBallot + status, className = "two-column-grid")
        val electionsLink = link(text = "Elections") {
            handleEvent(Events.NavigateToElectionsRequest)
        }
        val homeLink = link(text = "Home") {
            handleEvent(Events.NavigateToHomeRequest)
        }
        val logoutLink = link(text = "Logout") {
            handleEvent(Events.LogoutRequest)
        }

        val list = listOf(caption) + error + grid + electionsLink + homeLink + logoutLink
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = null)
    }

    private fun labelText(caption: String, value: Any?): List<HTMLElement> {
        val label = Html.span(caption)
        val text = Html.input(value?.toString() ?: "", "")
        return listOf(label, text)
    }
}
