data class Model(val page: String,
                 val login: LoginModel,
                 val register: RegisterModel,
                 val home: HomeModel,
                 val debug: DebugModel) {
    fun withLoginError(error: String?): Model {
        val newLogin = login.copy(error = error)
        return copy(page = "login", login = newLogin)
    }

    fun withRegisterError(error: String?): Model {
        val newRegister = register.copy(error = error)
        return copy(page = "register", register = newRegister)
    }

    fun withUnsupportedError(error: String?): Model {
        val newDebug = debug.copy(error = error)
        return copy(page = "debug", debug = newDebug)
    }

    fun withLogin(): Model {
        val newLogin = login.copy(error = null)
        return copy(page = "login", login = newLogin)
    }

    fun withRegister(): Model {
        val newRegister = register.copy(error = null)
        return copy(page = "register", register = newRegister)
    }

    fun withHome(): Model {
        val newHome = home.copy(error = null)
        return copy(page = "home", home = newHome)
    }

    fun withHome(name: String): Model {
        val newHome = home.copy(name = name, error = null)
        return copy(page = "home", home = newHome)
    }

    fun withDebug(): Model {
        val newDebug = debug.copy(error = null)
        return copy(page = "debug", debug = newDebug)
    }

    companion object {
        val empty = Model(
                page = "login",
                login = LoginModel(error = null),
                register = RegisterModel(error = null),
                home = HomeModel(name = "", error = null),
                debug = DebugModel(error = null))

        data class LoginModel(val error: String?)
        data class RegisterModel(val error: String?)
        data class HomeModel(val name: String, val error: String?)
        data class DebugModel(val error: String?)
    }
}
