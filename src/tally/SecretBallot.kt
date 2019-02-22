package tally

data class SecretBallot(private val confirmation: String,
                        private val rankings: List<Ranking>) : Comparable<SecretBallot> {
    fun toRow(): List<Any> =
            listOf(confirmation, *(rankings.flatMap { it.toRow() }.toTypedArray()))

    override fun compareTo(other: SecretBallot): Int =
            confirmation.compareTo(other.confirmation)
}
