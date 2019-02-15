object CondorcetComponents : Components {
    private val map: Map<String, Renderable> = mapOf(
            Pair("login", Login()),
            Pair("register", Register()),
            Pair("home", Home()),
            Pair("elections", Elections()),
            Pair("debug", Debug())
    )

    override fun get(name: String): Renderable =
            map[name] ?: throw RuntimeException("Component named \"$name\" not found")
}
