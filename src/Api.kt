import kotlin.js.Promise

interface Api {
    fun login(nameOrEmail: String, password: String): Promise<LoginResponse>
    fun register(name: String, email: String, password: String): Promise<RegisterResponse>
    data class LoginResponse(val name: String)
    data class RegisterResponse(val name: String)
}
