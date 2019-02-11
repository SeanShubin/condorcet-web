object CondorcetComponents : Components {
    private val map: Map<String, Renderable> = mapOf(
            Pair("login", Login()),
            Pair("register", Register()),
            Pair("home", Home())
    )

    override fun get(name: String): Renderable = map.getValue(name)
}
