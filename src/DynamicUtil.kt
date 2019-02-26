import kotlin.js.Date

object DynamicUtil {
    inline fun <reified T : Any> castOrNull(a: dynamic): T? = try {
        a as T
    } catch (ex: ClassCastException) {
        null
    }

    fun toDate(a: dynamic): Date? = try {
        Date(a as String)
    } catch (ex: ClassCastException) {
        null
    }

    inline fun <reified T : Enum<T>> toEnum(a: dynamic): T? = try {
        val name = a?.`name$` as String
        enumValueOf<T>(name)
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
