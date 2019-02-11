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

    private fun appendChildren(element: HTMLElement, children: List<HTMLElement>) {
        children.forEach { element.append(it) }
    }
}
