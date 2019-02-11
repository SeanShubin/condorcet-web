interface Effect {
    fun apply(model: Model,
              environment: Environment,
              components: Components,
              handleEvent: (GenericEvent) -> Unit)
}
