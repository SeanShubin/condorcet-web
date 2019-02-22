package tally

data class Ranking(val rank: Int,
                   val candidate: String) {
    fun toRow(): List<Any> = listOf(rank, candidate)
    fun validate(candidates: List<String>) {
        if (!candidates.contains(candidate)) {
            val validCandidates = candidates.joinToString(", ")
            throw RuntimeException("invalid candidate $candidate, valid candidates are $validCandidates")
        }
    }
}
