import org.w3c.dom.HTMLElement

interface Renderable{
    fun render(model: Model, handleEvent: (GenericEvent) -> Unit): HTMLElement
}
