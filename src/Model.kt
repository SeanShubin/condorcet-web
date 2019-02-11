data class Model(val page: String,
                 val error: String?,
                 val home: HomeModel?) {
    companion object {
        val empty = Model(page = "login", error = null, home = null)

        data class HomeModel(val name: String)
    }
}
