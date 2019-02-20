object DynamicUtil {
    inline fun <reified T : Any> castOrNull(a: dynamic): T? = try {
        a as T
    } catch (ex: ClassCastException) {
        null
    }

    inline fun <reified T : Any> castArrayToListOrNull(a: dynamic): List<T>? = try {
        val asArray = a as Array<T>
        asArray.toList()
    } catch (ex: ClassCastException) {
        null
    }
}