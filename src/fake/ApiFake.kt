package fake

import Api
import Api.*
import Api.ElectionStatus.EDITING
import kotlin.js.Date
import kotlin.js.Promise

class ApiFake : Api {
    private val users: MutableList<User> = mutableListOf()
    private val elections: MutableList<Election> = mutableListOf()
    override fun login(nameOrEmail: String, password: String): Promise<LoginResponse> {
        val user = userByNameOrEmail(nameOrEmail)
        return if (user == null) {
            Promise.reject(RuntimeException("User \"$nameOrEmail\" does not exist"))
        } else {
            if (password == user.password) {
                Promise.resolve(LoginResponse(user.name))
            } else {
                Promise.reject(RuntimeException("Bad password for user \"$nameOrEmail\""))
            }
        }
    }

    override fun register(name: String, email: String, password: String): Promise<RegisterResponse> {
        if (userNameExists(name)) return Promise.reject(RuntimeException("User named \"$name\" already exists"))
        if (userEmailExists(email)) return Promise.reject(RuntimeException("User with email \"$email\" already exists"))
        val user = User(name, email, password)
        users.add(user)
        return Promise.resolve(RegisterResponse(name))
    }

    override fun createElection(credential: Credential, electionName: String): Promise<CreateElectionResponse> {
        requireLoggedIn(credential)
        if (electionNameExists(electionName)) return Promise.reject(RuntimeException("Election \"$electionName\" already exists"))
        val election = Election(
                credential.name,
                electionName,
                start = null,
                end = null,
                secretBallot = true,
                status = EDITING)
        elections.add(election)
        return Promise.resolve(CreateElectionResponse(election))
    }

    override fun getElection(credential: Credential, electionName: String): Promise<Api.GetElectionResponse> {
        requireLoggedIn(credential)
        val election = electionByName(electionName)
        return if (election == null) {
            Promise.reject(RuntimeException("Election \"$electionName\" does not exist"))
        } else {
            Promise.resolve(Api.GetElectionResponse(election))
        }
    }

    override fun listElections(credential: Credential): Promise<Api.ListElectionsResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun updateElectionStart(credential: Credential, start: Date?): Promise<Api.UpdateElectionResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun updateElectionEnd(credential: Credential, end: Date?): Promise<Api.UpdateElectionResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun updateElectionSecretBallot(credential: Credential, secretBallot: Boolean): Promise<Api.UpdateElectionResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun addCandidates(credential: Credential, electionName: String, candidates: List<String>): Promise<Api.UpdateCandidatesResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun removeCandidate(credential: Credential, electionName: String, candidate: String): Promise<Api.UpdateCandidatesResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun setEligibleVoter(credential: Credential, electionName: String, voterName: String, eligable: Boolean): Promise<Api.SetEligibleVotersResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun closeElectionForEdits(credential: Credential, electionName: String): Promise<Api.CloseElectionForEditsResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun startElection(credential: Credential, electionName: String): Promise<Api.StartElectionResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun endElection(credential: Credential, electionName: String): Promise<Api.EndElectionResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun castBallot(credential: Credential, electionName: String, rankings: List<Api.Ranking>): Promise<Api.CastBallotResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun abstain(credential: Credential, electionName: String, rankings: List<Api.Ranking>): Promise<Api.AbstainResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun castEmptyVote(credential: Credential, electionName: String, rankings: List<Api.Ranking>): Promise<Api.CastEmptyVoteResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun tally(credential: Credential, electionName: String): Promise<Api.TallyResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    override fun tallyDetail(credential: Credential, electionName: String): Promise<Api.TallyDetailResponse> {
        requireLoggedIn(credential)
        TODO("not implemented")
    }

    private fun userByName(name: String): User? = users.find { it.name == name }
    private fun electionByName(name: String): Election? = elections.find { it.name == name }
    private fun userByEmail(email: String): User? = users.find { it.email == email }
    private fun userByNameOrEmail(nameOrEmail: String): User? = userByName(nameOrEmail) ?: userByEmail(nameOrEmail)
    private fun userNameExists(name: String): Boolean = userByName(name) != null
    private fun userEmailExists(email: String): Boolean = userByEmail(email) != null
    private fun electionNameExists(name: String): Boolean = electionByName(name) != null
    private fun requireLoggedIn(credential: Credential) {
        val user = userByName(credential.name)
        if (user == null || user.password != credential.password) {
            throw RuntimeException("Logged in user required")
        }
    }
}
