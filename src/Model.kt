data class Model(val page:String, val error:String?) {
    companion object {
        val empty = Model(page = "login", error = null)
    }
}
