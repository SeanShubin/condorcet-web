import org.w3c.dom.HTMLElement

interface Renderable{
    fun render(handleEvent:(GenericEvent)->Unit):HTMLElement
}
