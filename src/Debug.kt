import Html.div
import Html.header
import Html.pre
import Html.span

class Debug : Renderable {
    override fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused {
        val debugModel = model.debug
        val caption = header("Debug")
        val error = if (debugModel.error == null) listOf() else listOf(span(debugModel.error, "error"))
        val modelString = pre(JSON.stringify(model))
        val list = listOf(caption) + error + listOf(modelString)
        val rendered = div(contents = list, className = "single-column-flex")
        return RenderedAndFocused(rendered, focused = null)
    }
}
