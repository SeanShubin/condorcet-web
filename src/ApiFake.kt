import Api.LoginResponse
import kotlin.js.Promise

class ApiFake : Api {
    override fun login(nameOrEmail: String, password: String): Promise<LoginResponse> {
        return Promise.resolve(LoginResponse(nameOrEmail))
    }
}
