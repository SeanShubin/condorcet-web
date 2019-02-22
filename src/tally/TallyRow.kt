package tally

data class TallyRow(private val place: Int,
                    private val candidates: List<String>) {
    fun toRow(): List<Any> = listOf(placeString(), *candidates.sorted().toTypedArray())

    private fun placeString(): String = when (place) {
        1 -> "1st"
        2 -> "2nd"
        3 -> "3rd"
        else -> "${place}th"
    }
}
