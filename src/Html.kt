import org.w3c.dom.*
import kotlin.browser.document
import kotlin.dom.addClass

object Html{
    fun header(text: String): HTMLElement {
        val element = document.createElement("h1") as HTMLElement
        element.textContent = text
        return element
    }

    fun input(placeholder: String): HTMLInputElement {
        val input = document.createElement("input") as HTMLInputElement
        input.placeholder = placeholder
        return input
    }

    fun password(placeholder: String): HTMLInputElement {
        val password = input(placeholder = placeholder)
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

    private fun appendChildren(element: HTMLElement, children: List<HTMLElement>) {
        children.forEach { element.append(it) }
    }
}
