package tally

import tally.Matrix.Companion.plus

data class MatrixBuilder(val matrix: Matrix, val candidates: List<String>) {
    private val candidateToIndexMap: Map<String, Int> = mapOf(*(candidates zip candidates.indices).toTypedArray())
    fun processBallot(ballot: Ballot): MatrixBuilder = MatrixBuilder(matrix + ballotToMatrix(ballot), candidates)

    fun build(): Matrix = matrix

    private fun ballotToMatrix(ballot: Ballot): Matrix {
        val pairwisePreferences = ballot.pairwisePreferences(candidates)
        val pairwisePreferenceIndices = pairwisePreferences.map { candidatePairToIndices(it) }
        val valueMap = pairwisePreferenceIndices.map { Pair(it, 1) }.toMap().withDefault { 0 }
        val size = candidates.size
        return Matrix.ofSizeWithGenerator(size, size, { i, j -> valueMap.getValue(Pair(i, j)) })
    }

    private fun candidatePairToIndices(candidatePair: Pair<String, String>): Pair<Int, Int> {
        val (stronger, weaker) = candidatePair
        return Pair(candidateToIndexMap.getValue(stronger), candidateToIndexMap.getValue(weaker))
    }

    companion object {
        fun empty(candidates: List<String>): MatrixBuilder = MatrixBuilder(
                Matrix.ofSizeWithDefault(candidates.size, candidates.size, 0),
                candidates)

        val processBallot: (matrixBuilder: MatrixBuilder, ballot: Ballot) -> MatrixBuilder = { matrixBuilder, ballot ->
            matrixBuilder.processBallot(ballot)
        }
    }
}
