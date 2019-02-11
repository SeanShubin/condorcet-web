interface Renderable {
    fun render(model: Model, handleEvent: (GenericEvent) -> Unit): RenderedAndFocused
}
