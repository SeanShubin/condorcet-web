package tally

data class Ballot(val id: String,
                  val confirmation: String,
                  val rankings: List<Ranking>) {
    init {
        if (candidates().distinct().size != rankings.size)
            throw RuntimeException("Ballots for $id has duplicate candidates: ${candidates().joinToString(",")}")
    }

    fun toRow(): List<Any> =
            listOf(id, confirmation, *(rankings.flatMap { it.toRow() }.toTypedArray()))

    fun pairwisePreferences(allCandidates: List<String>): List<Pair<String, String>> {
        val candidateRankMap = buildCandidateRankMap()
        fun betterRankThan(left: String, right: String): Boolean {
            val leftRank = candidateRankMap[left]
            val rightRank = candidateRankMap[right]
            if (leftRank == null) return false
            if (rightRank == null) return true
            return leftRank < rightRank
        }

        val result = mutableListOf<Pair<String, String>>()
        for (i in allCandidates) {
            for (j in allCandidates) {
                if (betterRankThan(i, j)) {
                    result.add(Pair(i, j))
                }
            }
        }
        return result
    }

    fun validate(candidates: List<String>) {
        rankings.forEach { it.validate(candidates) }
    }

    private fun buildCandidateRankMap(): Map<String, Int> =
            rankings.map { buildCandidateRankEntry(it) }.toMap()

    private fun buildCandidateRankEntry(ranking: Ranking): Pair<String, Int> =
            Pair(ranking.candidate, ranking.rank)

    fun candidates(): List<String> = rankings.map { it.candidate }

    companion object {
        private val spacePattern = Regex("\\s+")
        fun fromString(s: String): Ballot {
            val words = s.split(spacePattern)
            val id = words[0]
            val confirmation = words[1]
            val rankings = (2 until words.size step 2).map { i -> Ranking(words[i].toInt(), words[i + 1]) }
            return Ballot(id, confirmation, rankings)
        }
    }
}
