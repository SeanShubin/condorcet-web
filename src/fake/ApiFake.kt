package fake

import Api
import Api.LoginResponse
import Api.RegisterResponse
import kotlin.js.Promise

class ApiFake : Api {
    private val users: MutableList<User> = mutableListOf()
    private val elections: MutableList<Election>= mutableListOf()
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
        if (nameExists(name)) return Promise.reject(RuntimeException("User named \"$name\" already exists"))
        if (emailExists(email)) return Promise.reject(RuntimeException("User with email \"$email\" already exists"))
        val user = User(name, email, password)
        users.add(user)
        return Promise.resolve(RegisterResponse(name))
    }

    override fun createElection(credential: Credential, electionName: String): Promise<CreateElectionResponse>

    private fun userByName(name: String): User? = users.find { it.name == name }
    private fun userByEmail(email: String): User? = users.find { it.email == email }
    private fun userByNameOrEmail(nameOrEmail: String): User? = userByName(nameOrEmail) ?: userByEmail(nameOrEmail)
    private fun nameExists(name: String): Boolean = userByName(name) != null
    private fun emailExists(email: String): Boolean = userByEmail(email) != null
}
