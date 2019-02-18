import CondorcetEvents.LogoutRequest
import CondorcetEvents.NavigateToHomeRequest
import Html.caption
import Html.div
import Html.header
import Html.link
import Html.span
import Html.table
import Html.tbody
import Html.theadFromStrings
import Html.trFromStrings

class ElectionsPage : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val electionsModel = model.elections
        val caption = header("Elections")
        val error = if (electionsModel.error == null) listOf() else listOf(span(electionsModel.error, "error"))

        val tableCaption = caption("Elections")
        val tableHeader = theadFromStrings(Api.Election.columnNames)
        val tableRows = electionsModel.electionList.map { trFromStrings(it.toRow()) }
        val tableBody = tbody(tableRows)

        val electionsTable = table(tableCaption, tableHeader, tableBody)
        val homeLink = link(text = "Home") {
            handleEvent(NavigateToHomeRequest)
        }
        val logoutLink = link(text = "Logout") {
            handleEvent(LogoutRequest)
        }
        val list = listOf(caption) + error + listOf(electionsTable, homeLink, logoutLink)
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = null)
    }
}
