import DynamicUtil.castArrayToListOrNull
import DynamicUtil.castOrNull

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

    fun createElectionPage(): Model {
        return copy(page = "create-election")
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

        fun fromDynamic(jsonObject: dynamic): Model {
            val page = jsonObject.page
            console.log("jsonObject", jsonObject)
            val login = LoginModel(
                    nameOrEmail = castOrNull(jsonObject?.login?.nameOrEmail) ?: empty.login.nameOrEmail,
                    password = castOrNull(jsonObject?.login?.password) ?: empty.login.password,
                    error = castOrNull(jsonObject?.login?.error) ?: empty.login.error
            )
            val register = RegisterModel(
                    name = castOrNull(jsonObject?.register?.name) ?: empty.register.name,
                    email = castOrNull(jsonObject?.register?.email) ?: empty.register.email,
                    password = castOrNull(jsonObject?.register?.password) ?: empty.register.password,
                    confirmPassword = castOrNull(jsonObject?.register?.confirmPassword)
                            ?: empty.register.confirmPassword,
                    error = castOrNull(jsonObject?.register?.error) ?: empty.register.error
            )
            val home = HomeModel(
                    name = castOrNull(jsonObject?.home?.name) ?: empty.home.name,
                    error = castOrNull(jsonObject?.home?.error) ?: empty.home.error
            )
            val elections = ElectionsModel(
                    electionList = (castArrayToListOrNull(jsonObject?.elections?.electionList)
                            ?: empty.elections.electionList).map {
                        Api.Election.fromDynamic(it)
                    },
                    error = castOrNull(jsonObject.elections.error) ?: empty.elections.error
            )
            val debug = DebugModel(
                    error = castOrNull(jsonObject.debug.error) ?: empty.debug.error
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

        fun fromString(string: String): Model {
            val jsonObject: dynamic = JSON.parse(string)
            return fromDynamic(jsonObject)
        }
    }
}
