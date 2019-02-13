import kotlin.js.Date
import kotlin.js.Promise

interface Api {
    fun login(nameOrEmail: String, password: String): Promise<LoginResponse>
    fun register(name: String, email: String, password: String): Promise<RegisterResponse>
    fun createElection(credential: Credential, electionName: String): Promise<CreateElectionResponse>
    fun getElection(credential: Credential, electionName: String): Promise<GetElectionResponse>
    fun updateElection(credential: Credential,
                       start: Date?,
                       end: Date?,
                       secretBallot: Boolean?,
                       allowMonitoring: Boolean?): Promise<UpdateElectionResponse>

    fun setCandidates(credential: Credential, electionName: String, candidates: List<String>): Promise<SetCandidatesResponse>
    fun setEligibleVoters(credential: Credential, electionName: String, voters: List<String>): Promise<SetEligibleVotersResponse>
    fun closeElectionForEdits(credential: Credential, electionName: String): Promise<CloseElectionForEditsResponse>
    fun startElection(credential: Credential, electionName: String): Promise<StartElectionResponse>
    fun endElection(credential: Credential, electionName: String): Promise<EndElectionResponse>
    fun listElections(credential: Credential): Promise<ListElectionsResponse>
    fun castBallot(credential: Credential, electionName: String, rankings: List<Ranking>): Promise<CastBallotResponse>
    fun abstain(credential: Credential, electionName: String, rankings: List<Ranking>): Promise<AbstainResponse>
    fun castEmptyVote(credential: Credential, electionName: String, rankings: List<Ranking>): Promise<CastEmptyVoteResponse>
    fun tally(credential: Credential, electionName: String): Promise<TallyResponse>
    fun tallyDetail(credential: Credential, electionName: String): Promise<TallyDetailResponse>

    data class LoginResponse(val name: String)
    data class RegisterResponse(val name: String)
    data class Credential(val name: String, val password: String)
    data class Election(val creatorName: String,
                        val name: String,
                        val start: Date?,
                        val end: Date?,
                        val secretBallot: Boolean,
                        val allowMonitoring: Boolean,
                        val status: ElectionStatus)

    data class Ranking(val rank: Int, val candidateName: String)
    enum class ElectionStatus {
        EDITING, // may still change, not started yet
        PENDING, // closed for changes, not started yet
        RUNNING, // ballots may be cast
        CONCLUDED // election is over
    }

    data class Ballot(val ballotId: String, val rankings: List<String>)
    data class TallyDetail(val electionName: String,
                           val candidates: List<String>,
                           val voted: List<String>,
                           val didNotVote: List<String>,
                           val preferenceMatrix: List<List<Int>>,
                           val strongestPathMatrix: List<List<Int>>,
                           val ballots: List<Ballot>)

    data class CreateElectionResponse(val election: Election)
    data class UpdateElectionResponse(val election: Election)
    data class GetElectionResponse(val election: Election)
    data class SetCandidatesResponse(val electionName: String, val candidates: List<String>)
    data class SetEligibleVotersResponse(val electionName: String, val voters: List<String>)
    object StartElectionResponse
    object EndElectionResponse
    object CloseElectionForEditsResponse
    data class ListElectionsResponse(val elections: List<Election>)
    object CastBallotResponse
    object AbstainResponse
    object CastEmptyVoteResponse
    data class TallyResponse(val electionName: String, val rankings: List<Ranking>)
    data class TallyDetailResponse(val tallyDetail: TallyDetail)
}
