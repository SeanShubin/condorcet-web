import kotlin.dom.clear

object CondorcetEffects{
    object Render: Effect{
        override fun apply(model: Model,
                           environment: Environment,
                           components: Components,
                           handleEvent: (GenericEvent) -> Unit) {
            val body = environment.document.body!!
            body.clear()
            val page = model.page
            val component = components[page]
            val rendered = component.render(handleEvent)
            body.appendChild(rendered)
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