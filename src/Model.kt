data class Model(val page: String,
                 val credential: Api.Credential,
                 val login: LoginModel,
                 val register: RegisterModel,
                 val home: HomeModel,
                 val elections: ElectionsModel,
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

    fun loginPage(): Model {
        val newLogin = login.copy(error = null)
        return copy(page = "login", login = newLogin)
    }

    fun registerPage(): Model {
        val newRegister = register.copy(error = null)
        return copy(page = "register", register = newRegister)
    }

    fun homePage(): Model {
        val newHome = home.copy(error = null)
        return copy(page = "home", home = newHome)
    }

    fun homePage(name: String): Model {
        val newHome = home.copy(name = name, error = null)
        return copy(page = "home", home = newHome)
    }

    fun debugPage(): Model {
        val newDebug = debug.copy(error = null)
        return copy(page = "debug", debug = newDebug)
    }

    fun electionsPage(): Model {
        val newElections = elections.copy(error = null)
        return copy(page = "elections", elections = newElections)
    }

    fun electionsList(electionList:List<Api.Election>):Model {
        val newElectionsModel = elections.copy(electionList = electionList)
        return copy(elections = newElectionsModel)
    }

    fun withElectionsError(error: String?): Model {
        val newElections = elections.copy(error = error)
        return copy(page = "elections", elections = newElections)
    }

    fun purgePasswords(): Model =
            copy(login = login.copy(password = ""), register = register.copy(password = "", confirmPassword = ""))

    data class LoginModel(val nameOrEmail: String, val password: String, val error: String?)
    data class RegisterModel(val name: String, val email: String, val password: String, val confirmPassword: String, val error: String?)
    data class HomeModel(val name: String, val error: String?)
    data class ElectionsModel(val electionList:List<Api.Election>, val error: String?)
    data class DebugModel(val error: String?)

    companion object {
        val empty = Model(
                page = "login",
                credential = Api.Credential(
                        name = "",
                        password = ""
                ),
                login = LoginModel(
                        nameOrEmail = "",
                        password = "",
                        error = null),
                register = RegisterModel(
                        name = "",
                        email = "",
                        password = "",
                        confirmPassword = "",
                        error = null),
                home = HomeModel(name = "", error = null),
                elections = ElectionsModel(electionList = emptyList(), error = null),
                debug = DebugModel(error = null))

        fun fromString(string: String): Model {
            val jsonObject = JSON.parse<Model>(string)
            val page = jsonObject.page
            val login = LoginModel(
                    nameOrEmail = jsonObject.login.nameOrEmail,
                    password = jsonObject.login.password,
                    error = jsonObject.login.error
            )
            val register = RegisterModel(
                    name = jsonObject.register.name,
                    email = jsonObject.register.email,
                    password = jsonObject.register.password,
                    confirmPassword = jsonObject.register.confirmPassword,
                    error = jsonObject.register.error
            )
            val home = HomeModel(
                    name = jsonObject.home.name,
                    error = jsonObject.home.error
            )
            val elections = ElectionsModel(
                    electionList = jsonObject.elections.electionList,
                    error = jsonObject.elections.error
            )
            val debug = DebugModel(
                    error = jsonObject.debug.error
            )
            val credential = Api.Credential(jsonObject.credential.name, jsonObject.credential.password)
            return Model(
                    page = page,
                    credential = credential,
                    login = login,
                    register = register,
                    home = home,
                    elections = elections,
                    debug = debug)
        }
    }
}
