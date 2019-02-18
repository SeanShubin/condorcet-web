import CondorcetEvents.LogoutRequest
import CondorcetEvents.NavigateToHomeRequest
import Html.div
import Html.header
import Html.link
import Html.span
import org.w3c.dom.HTMLTableColElement

class ElectionsPage : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val electionsModel = model.elections
        val caption = header("Elections")
        val error = if (electionsModel.error == null) listOf() else listOf(span(electionsModel.error, "error"))

        val headerRow = electionListToHeaderRow(model.elections.electionList)
        val rows = electionListToRows(model.elections.electionList)
        val table = Html.table(headerRow, rows)
        val homeLink = link(text = "Home") {
            handleEvent(NavigateToHomeRequest)
        }
        val logoutLink = link(text = "Logout") {
            handleEvent(LogoutRequest)
        }
        val list = listOf(caption) + error + table + listOf(homeLink, logoutLink)
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = null)
    }

    private fun electionListToHeaderRow(list:List<Api.Election>): HTMLTableColElement{
        
    }
}
