package fake

import Api
import Api.*
import Api.ElectionStatus.EDITING
import kotlin.js.Date
import kotlin.js.Promise

class ApiFake : Api {
    private val users: MutableList<User> = mutableListOf()
    private val elections: MutableList<Election> = mutableListOf()
    private val candidates: MutableList<Candidate> = mutableListOf()
    private val voters: MutableList<Voter> = mutableListOf()
    private val ballots: MutableList<Ballot> = mutableListOf()
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

    override fun getElection(credential: Credential, electionName: String): Promise<GetElectionResponse> {
        requireLoggedIn(credential)
        val election = electionByName(electionName)
        return if (election == null) {
            Promise.reject(RuntimeException("Election \"$electionName\" does not exist"))
        } else {
            Promise.resolve(GetElectionResponse(election))
        }
    }

    override fun listElections(credential: Credential): Promise<ListElectionsResponse> {
        requireLoggedIn(credential)
        return Promise.resolve(ListElectionsResponse(elections))
    }

    override fun updateElectionStart(credential: Credential, electionName: String, start: Date?): Promise<UpdateElectionResponse> {
        return updateElection(credential, electionName) { it.copy(start = start) }
    }

    override fun updateElectionEnd(credential: Credential, electionName: String, end: Date?): Promise<UpdateElectionResponse> {
        return updateElection(credential, electionName) { it.copy(end = end) }
    }

    override fun updateElectionSecretBallot(credential: Credential, electionName: String, secretBallot: Boolean): Promise<UpdateElectionResponse> {
        return updateElection(credential, electionName) { it.copy(secretBallot = secretBallot) }
    }

    override fun updateElectionStatus(credential: Credential, electionName: String, status: ElectionStatus): Promise<UpdateElectionResponse> {
        return updateElection(credential, electionName) { it.copy(status = status) }
    }

    override fun addCandidates(credential: Credential, electionName: String, candidateNames: List<String>): Promise<UpdateCandidatesResponse> {
        requireCanEditElection(credential, electionName)
        candidateNames.forEach(addCandidateToElection(electionName))
        return Promise.resolve(UpdateCandidatesResponse(electionName, candidatesForElection(electionName).map { it.candidateName }))
    }

    override fun removeCandidate(credential: Credential, electionName: String, candidateName: String): Promise<UpdateCandidatesResponse> {
        requireCanEditElection(credential, electionName)
        val candidate = Candidate(electionName, candidateName)
        candidates.remove(candidate)
        return Promise.resolve(UpdateCandidatesResponse(electionName, candidatesForElection(electionName).map { it.candidateName }))
    }

    override fun addVoters(credential: Credential, electionName: String, voterNames: List<String>): Promise<UpdateVotersResponse> {
        requireCanEditElection(credential, electionName)
        voterNames.forEach(addVoterToElection(electionName))
        return Promise.resolve(UpdateVotersResponse(electionName, votersForElection(electionName).map { it.voterName }))
    }

    override fun removeVoter(credential: Credential, electionName: String, voterName: String): Promise<UpdateVotersResponse> {
        requireCanEditElection(credential, electionName)
        val voter = Voter(electionName, voterName)
        voters.remove(voter)
        return Promise.resolve(UpdateVotersResponse(electionName, votersForElection(electionName).map { it.voterName }))
    }

    override fun setBallot(credential: Credential, electionName: String, rankings: List<Ranking>): Promise<SetBallotResponse> {
        requireCanVote(credential, electionName)
        val index = ballots.indexOfFirst { existingBallot -> existingBallot.electionName == electionName && existingBallot.voterName == credential.name }
        val ballot = Ballot(electionName, credential.name, rankings)
        if (index == -1) {
            ballots.add(ballot)
        } else {
            ballots[index] = ballot
        }
        return Promise.resolve(SetBallotResponse)
    }

    override fun removeBallot(credential: Credential, electionName: String): Promise<RemoveBallotResponse> {
        requireCanVote(credential, electionName)
        val index = ballots.indexOfFirst { existingBallot -> existingBallot.electionName == electionName && existingBallot.voterName == credential.name }
        if (index != -1) {
            ballots.removeAt(index)
        }
        return Promise.resolve(RemoveBallotResponse)
    }

    data class Candidate(val electionName: String, val candidateName: String)
    data class Voter(val electionName: String, val voterName: String)
    data class Ballot(val electionName: String, val voterName: String, val rankings: List<Ranking>)

    private fun updateElection(credential: Credential, electionName: String, update: (Election) -> Election): Promise<UpdateElectionResponse> {
        requireCanEditElection(credential, electionName)
        val indexedElection = indexedElectionByName(electionName)
        return if (indexedElection == null) {
            Promise.reject(RuntimeException("Election \"$electionName\" does not exist"))
        } else {
            val (index, election) = indexedElection
            val newElection = update(election)
            elections[index] = newElection
            Promise.resolve(UpdateElectionResponse(newElection))
        }
    }

    private fun userByName(name: String): User? = users.find { it.name == name }
    private fun electionByName(name: String): Election? = elections.find { it.name == name }
    private fun indexedElectionByName(name: String): Pair<Int, Election>? {
        elections.forEachIndexed { index, election ->
            if (election.name == name) {
                return Pair(index, election)
            }
        }
        return null
    }

    private fun userByEmail(email: String): User? = users.find { it.email == email }
    private fun userByNameOrEmail(nameOrEmail: String): User? = userByName(nameOrEmail) ?: userByEmail(nameOrEmail)
    private fun userNameExists(name: String): Boolean = userByName(name) != null
    private fun userEmailExists(email: String): Boolean = userByEmail(email) != null
    private fun electionNameExists(name: String): Boolean = electionByName(name) != null
    private fun votersForElection(electionName: String): List<Voter> = voters.filter { it.electionName == electionName }
    private fun candidatesForElection(electionName: String): List<Candidate> = candidates.filter { it.electionName == electionName }
    private fun requireLoggedIn(credential: Credential) {
        val user = userByName(credential.name)
        if (user == null || user.password != credential.password) {
            throw RuntimeException("Logged in user required")
        }
    }

    private fun requireCanEditElection(credential: Credential, electionName: String) {
        requireLoggedIn(credential)
        val election = electionByName(electionName)
        when {
            election == null -> throw RuntimeException("Election \"$electionName\" does not exist")
            election.status != ElectionStatus.EDITING -> throw RuntimeException("Election \"$electionName\" is no longer editable")
            election.owner != credential.name -> throw RuntimeException("User \"${credential.name} is not the owner of election \"$electionName\"")
            else -> {
                val user = userByName(credential.name)
                if (user == null || user.password != credential.password) {
                    throw RuntimeException("Logged in user required")
                }
            }
        }
    }

    private fun requireCanVote(credential: Credential, electionName: String) {
        requireLoggedIn(credential)
        if (!electionNameExists(electionName)) throw RuntimeException("Election \"$electionName\" does not exist")
        val voters = votersForElection(electionName)
        if (!voters.any { it.voterName == credential.name }) {
            throw RuntimeException("User ${credential.name} is not eligible to vote in election $electionName")
        }
    }

    private fun addCandidateToElection(candidateName: String): (String) -> Unit = { electionName ->
        val candidate = Candidate(electionName, candidateName)
        if (!candidates.contains(candidate)) {
            candidates.add(candidate)
        }
    }

    private fun addVoterToElection(voterName: String): (String) -> Unit = { electionName ->
        val voter = Voter(electionName, voterName)
        if (!voters.contains(voter)) {
            voters.add(voter)
        }
    }
}
