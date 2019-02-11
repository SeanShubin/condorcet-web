import kotlin.js.Promise

interface Api {
    fun login(nameOrEmail: String, password: String): Promise<LoginResponse>
    data class LoginResponse(val name: String)
}
