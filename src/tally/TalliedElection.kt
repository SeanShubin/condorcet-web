package tally

data class TalliedElection(private val candidates: List<String>,
                           private val voted: List<String>,
                           private val didNotVote: List<String>,
                           private val secretBallots: List<SecretBallot>,
                           private val preferences: Matrix,
                           private val strongestPaths: Matrix,
                           private val tally: List<TallyRow>)
