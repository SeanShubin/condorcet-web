package tally

data class ElectionBuilder(private val mode: Mode = Mode.Unknown,
                           private val candidates: List<String> = emptyList(),
                           private val eligibleToVote: List<String> = emptyList(),
                           private val ballots: List<Ballot> = emptyList()) {
    enum class Mode(val parseName: String) {
        Candidates("candidates") {
            override fun processDataLine(builder: ElectionBuilder, line: String): ElectionBuilder =
                    builder.copy(candidates = builder.candidates + listOf(line))
        },
        Voters("eligible-to-vote") {
            override fun processDataLine(builder: ElectionBuilder, line: String): ElectionBuilder =
                    builder.copy(eligibleToVote = builder.eligibleToVote + listOf(line))
        },
        Ballots("ballots") {
            override fun processDataLine(builder: ElectionBuilder, line: String): ElectionBuilder =
                    builder.copy(ballots = builder.ballots + listOf(Ballot.fromString(line)))
        },
        Unknown("Unknown") {
            override fun processDataLine(builder: ElectionBuilder, line: String): ElectionBuilder {
                TODO("not implemented")
            }
        };

        companion object {
            fun fromString(s: String): Mode {
                for (value in values()) {
                    if (value.parseName == s.toLowerCase()) {
                        return value
                    }
                }
                return Unknown
            }
        }

        abstract fun processDataLine(builder: ElectionBuilder, line: String): ElectionBuilder
    }

    fun processLine(line: String): ElectionBuilder {
        return if (line.startsWith(" ")) {
            processDataLine(line.trim())
        } else {
            switchModes(line)
        }
    }

    private fun processDataLine(line: String): ElectionBuilder =
            mode.processDataLine(this, line)

    private fun switchModes(line: String): ElectionBuilder {
        val firstWord = line.split(" ")[0]
        return copy(mode = Mode.fromString(firstWord))
    }

    fun build(): Election = Election(candidates.sorted(), eligibleToVote, ballots)

    companion object {
        val EMPTY = ElectionBuilder()
        val processLine: (electionBuilder: ElectionBuilder, line: String) -> ElectionBuilder = { electionBuilder, line ->
            electionBuilder.processLine(line)
        }
    }
}
