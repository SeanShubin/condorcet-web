import kotlin.dom.clear

object CondorcetEffects{
    object Render: Effect{
        override fun apply(model: Model,
                           environment: Environment,
                           components: Components,
                           handleEvent: (GenericEvent) -> Unit) {
            console.log("Render", JSON.stringify(model))
            val body = environment.document.body!!
            body.clear()
            val page = model.page
            val component = components[page]
            val (rendered, focused) = component.render(model, handleEvent)
            body.appendChild(rendered)
            focused?.focus()
        }
    }

    class FireEvent(private val event: GenericEvent) : Effect {
        override fun apply(model: Model,
                           environment: Environment,
                           components: Components,
                           handleEvent: (GenericEvent) -> Unit) {
            handleEvent(event)
        }
    }
}