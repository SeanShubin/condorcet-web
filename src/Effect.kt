interface Effect {
    fun apply(model: Model,
              api: Api,
              environment: Environment,
              components: GenericComponents,
              handleEvent: (GenericEvent) -> Unit)
}
