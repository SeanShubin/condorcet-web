import Events.LogoutRequest
import Events.NavigateToHomeRequest
import Html.button
import Html.div
import Html.header
import Html.input
import Html.link
import Html.span
import Html.table
import Html.tbody
import Html.td
import Html.theadFromStrings
import Html.tr
import org.w3c.dom.HTMLTableRowElement

class ElectionsPage : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val electionsModel = model.elections
        val caption = header("Elections")
        val error = if (electionsModel.error == null) listOf() else listOf(span(electionsModel.error, "error"))
        val createElectionInput = input(text = "", placeholder = "Election Name")
        val createElectionAction = button("Create") {
            handleEvent(Events.CreateElectionRequest(createElectionInput.value))
        }
        val createElectionSpan = span(createElectionInput, createElectionAction)
        val tableHeader = theadFromStrings(listOf("edit") + Api.Election.columnNames)
        val tableRows = electionsModel.electionList.map { electionRow(it, handleEvent) }
        val tableBody = tbody(tableRows)
        val electionsTable = table(tableHeader, tableBody)

        val homeLink = link(text = "Home") {
            handleEvent(NavigateToHomeRequest)
        }
        val logoutLink = link(text = "Logout") {
            handleEvent(LogoutRequest)
        }
        val list = listOf(caption) + error + listOf(createElectionSpan, electionsTable, homeLink, logoutLink)
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = null)
    }

    private fun electionRow(election: Api.Election, handleEvent: (GenericEvent) -> Unit): HTMLTableRowElement {
        val buttonCaption = if (election.status == Api.ElectionStatus.EDITING) "Edit" else "View"
        val editButton = button(buttonCaption) {
            handleEvent(Events.NavigateToElectionRequest(election.name))
        }
        val editButtonCell = td(editButton)
        val remainingCells = election.toRow().map(::td)
        val cells = listOf(editButtonCell) + remainingCells
        return tr(cells)
    }
}
