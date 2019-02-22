object Components : GenericComponents {
    private val map: Map<String, Renderable> = mapOf(
            Pair("login", Login()),
            Pair("register", Register()),
            Pair("home", Home()),
            Pair("elections", ElectionsPage()),
            Pair("election", ElectionPage()),
            Pair("debug", Debug())
    )

    override fun get(name: String): Renderable =
            map[name] ?: throw RuntimeException("Component named \"$name\" not found")
}
