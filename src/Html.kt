import org.w3c.dom.*
import kotlin.browser.document
import kotlin.dom.addClass

object Html{
    fun header(text: String): HTMLElement {
        val element = document.createElement("h1") as HTMLElement
        element.textContent = text
        return element
    }

    fun input(text: String, placeholder: String): HTMLInputElement {
        val input = document.createElement("input") as HTMLInputElement
        input.placeholder = placeholder
        input.value = text
        return input
    }

    fun password(text: String, placeholder: String): HTMLInputElement {
        val password = input(text = text, placeholder = placeholder)
        password.type = "password"
        return password
    }

    fun button(text: String, onclick: () -> Unit): HTMLButtonElement {
        val button = document.createElement("button") as HTMLButtonElement
        button.type = "button"
        button.textContent = text
        button.onclick = { _ -> onclick() }
        return button
    }

    fun link(text: String, onclick: () -> Unit): HTMLAnchorElement {
        val link = document.createElement("a") as HTMLAnchorElement
        link.textContent = text
        link.href = "#"
        link.onclick = { _ -> onclick() }
        return link
    }

    fun div(contents: List<HTMLElement>, className: String): HTMLDivElement {
        val div = document.createElement("div") as HTMLDivElement
        appendChildren(div, contents)
        div.addClass(className)
        return div
    }

    fun span(text: String, className: String): HTMLSpanElement {
        val span = document.createElement("span") as HTMLSpanElement
        span.textContent = text
        span.addClass(className)
        return span
    }

    fun pre(text: String): HTMLPreElement {
        val span = document.createElement("pre") as HTMLPreElement
        span.textContent = text
        return span
    }

    fun td(text: String): HTMLTableCellElement {
        val tableCell = document.createElement("td") as HTMLTableCellElement
        tableCell.textContent = text
        return tableCell
    }

    fun td(element: HTMLElement): HTMLTableCellElement {
        val tableCell = document.createElement("td") as HTMLTableCellElement
        tableCell.appendChild(element)
        return tableCell
    }

    fun th(text: String): HTMLTableCellElement {
        val tableCell = document.createElement("th") as HTMLTableCellElement
        tableCell.textContent = text
        return tableCell
    }

    fun tr(vararg td: HTMLTableCellElement): HTMLTableRowElement {
        val tableRow = document.createElement("tr") as HTMLTableRowElement
        td.forEach {
            tableRow.appendChild(it)
        }
        return tableRow
    }

    fun tr(td: List<HTMLTableCellElement>): HTMLTableRowElement = tr(*td.toTypedArray())

    fun trFromStrings(vararg s: String): HTMLTableRowElement = tr(s.map { td(it) })

    fun trFromStrings(s: List<String>): HTMLTableRowElement = trFromStrings(*s.toTypedArray())

    fun headerTrFromStrings(vararg s: String): HTMLTableRowElement = tr(s.map { th(it) })

    fun headerTrFromStrings(s: List<String>): HTMLTableRowElement = headerTrFromStrings(*s.toTypedArray())

    fun caption(text: String): HTMLTableCaptionElement {
        val tableCaption = document.createElement("caption") as HTMLTableCaptionElement
        tableCaption.textContent = text
        return tableCaption
    }

    fun thead(header: HTMLTableRowElement): HTMLTableSectionElement {
        val tableHead = document.createElement("thead") as HTMLTableSectionElement
        tableHead.appendChild(header)
        return tableHead
    }

    fun thead(vararg cells: HTMLTableCellElement): HTMLTableSectionElement = thead(tr(*cells))

    fun thead(cells: List<HTMLTableCellElement>): HTMLTableSectionElement = thead(*cells.toTypedArray())

    fun theadFromStrings(vararg cells: String): HTMLTableSectionElement = thead(headerTrFromStrings(*cells))

    fun theadFromStrings(cells: List<String>): HTMLTableSectionElement = theadFromStrings(*cells.toTypedArray())

    fun tbody(vararg rows: HTMLTableRowElement): HTMLTableSectionElement {
        val tableBody = document.createElement("tbody") as HTMLTableSectionElement
        rows.forEach { tableBody.appendChild(it) }
        return tableBody
    }

    fun tbody(rows: List<HTMLTableRowElement>): HTMLTableSectionElement = tbody(*rows.toTypedArray())

    fun tbodyFromStrings(vararg rowStrings: List<String>): HTMLTableSectionElement = tbody(rowStrings.map { trFromStrings(it) })

    fun tbodyFromStrings(rows: List<List<String>>): HTMLTableSectionElement = tbodyFromStrings(*rows.toTypedArray())

    fun table(caption: HTMLTableCaptionElement, thead: HTMLTableSectionElement, tbody: HTMLTableSectionElement): HTMLTableElement {
        val tableElement = document.createElement("table") as HTMLTableElement
        tableElement.caption = caption
        tableElement.tHead = thead
        tableElement.appendChild(tbody)
        return tableElement
    }

    private fun appendChildren(element: HTMLElement, children: List<HTMLElement>) {
        children.forEach { element.append(it) }
    }
}
