import kotlin.js.Date

data class Minutes(val year: Int, val month: Int, val day: Int, val hours: Int, val minutes: Int) {
    override fun toString() = "${year.pad2()}-${month.pad2()}-${day.pad2()} ${hours.pad2()}:${minutes.pad2()}"
    fun localToUtc(): Minutes = fromDateUtc(toDate())
    fun utcToLocal(): Minutes = fromDate(toDateUtc())
    fun toDate(): Date = Date(year, month - 1, day, hours, minutes)
    fun toDateUtc(): Date = Date(Date.UTC(year, month - 1, day, hours, minutes))

    companion object {
        private val oneOrMoreNonDigit = Regex("""[^\d]+""")

        fun fromDate(date: Date): Minutes =
                Minutes(date.getFullYear(),
                        date.getMonth() + 1,
                        date.getDate(),
                        date.getHours(),
                        date.getMinutes())

        fun fromDateUtc(date: Date): Minutes =
                Minutes(date.getUTCFullYear(),
                        date.getUTCMonth() + 1,
                        date.getUTCDate(),
                        date.getUTCHours(),
                        date.getUTCMinutes())

        fun fromString(s: String): Minutes {
            val now = Minutes.fromDate(Date())
            val parts = s.split(oneOrMoreNonDigit)
            return now.copy(
                    year = lookupNumber(parts, 0) ?: now.year,
                    month = lookupNumber(parts, 1) ?: now.month,
                    day = lookupNumber(parts, 2) ?: now.day,
                    hours = lookupNumber(parts, 3) ?: 0,
                    minutes = lookupNumber(parts, 4) ?: 0)
        }

        private fun lookupNumber(parts: List<String>, index: Int): Int? =
                if (index >= parts.size) {
                    null
                } else try {
                    parts[index].toInt()
                } catch (ex: NumberFormatException) {
                    null
                }

        private fun Int.pad2(): String {
            val asString = toString()
            return if (asString.length < 2) {
                "0".repeat(2 - asString.length) + asString
            } else {
                asString
            }
        }
    }
}
