import kotlin.js.Date
import kotlin.js.Promise

interface Api {
    fun login(nameOrEmail: String, password: String): Promise<LoginResponse>
    fun register(name: String, email: String, password: String): Promise<RegisterResponse>
    fun createElection(credential: Credential, electionName: String): Promise<CreateElectionResponse>
    fun getElection(credential: Credential, electionName: String): Promise<GetElectionResponse>
    fun listElections(credential: Credential): Promise<ListElectionsResponse>

    fun updateElectionStart(credential: Credential, electionName: String, start: Date?): Promise<UpdateElectionResponse>
    fun updateElectionEnd(credential: Credential, electionName: String, end: Date?): Promise<UpdateElectionResponse>
    fun updateElectionSecretBallot(credential: Credential, electionName: String, secretBallot: Boolean): Promise<UpdateElectionResponse>
    fun updateElectionStatus(credential: Credential, electionName: String, status: ElectionStatus): Promise<UpdateElectionResponse>

    fun addCandidates(credential: Credential, electionName: String, candidateNames: List<String>): Promise<UpdateCandidatesResponse>
    fun removeCandidate(credential: Credential, electionName: String, candidateName: String): Promise<UpdateCandidatesResponse>

    fun addVoters(credential: Credential, electionName: String, voterNames: List<String>): Promise<UpdateVotersResponse>
    fun removeVoter(credential: Credential, electionName: String, voterName: String): Promise<UpdateVotersResponse>

    fun setBallot(credential: Credential, electionName: String, rankings: List<Ranking>): Promise<SetBallotResponse>
    fun removeBallot(credential: Credential, electionName: String): Promise<RemoveBallotResponse>

    data class Credential(val name: String, val password: String)
    data class Election(val owner: String,
                        val name: String,
                        val start: Date? = null,
                        val end: Date? = null,
                        val secretBallot: Boolean = true,
                        val status: ElectionStatus = ElectionStatus.EDITING) {
        fun toRow(): List<String> = listOf(
                owner,
                name,
                start?.toString() ?: "",
                end?.toString() ?: "",
                secretBallot.toString(),
                status.toString()
        )

        companion object {
            val columnNames = listOf("owner", "name", "start", "end", "secretBallot", "status")
        }
    }

    data class Ranking(val rank: Int, val candidateName: String)
    enum class ElectionStatus {
        EDITING, // may still change, not started yet
        PENDING, // closed for changes, not started yet
        RUNNING, // ballots may be cast
        CONCLUDED; // election is over

        override fun toString(): String = this.name.toLowerCase()
    }

    data class LoginResponse(val name: String)
    data class RegisterResponse(val name: String)
    data class CreateElectionResponse(val election: Election)
    data class UpdateElectionResponse(val election: Election)
    data class GetElectionResponse(val election: Election)
    data class UpdateCandidatesResponse(val electionName: String, val candidateNames: List<String>)
    data class UpdateVotersResponse(val electionName: String, val voterNames: List<String>)
    data class ListElectionsResponse(val elections: List<Election>)
    object SetBallotResponse
    object RemoveBallotResponse
}
