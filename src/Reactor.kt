object Reactor : GenericReactor {
    override fun react(model: Model, event: GenericEvent): Result {
        console.log("react.model", model)
//        console.log("react.event", event)
        val result = when (event) {
            is Events.Initialize -> initialize(model)
            is Events.LoginRequest -> loginRequest(model, event)
            is Events.LoginSuccess -> loginSuccess(model, event)
            is Events.LoginFailure -> loginFailure(model, event)
            is Events.RegisterRequest -> registerRequest(model, event)
            is Events.RegisterSuccess -> registerSuccess(model, event)
            is Events.RegisterFailure -> registerFailure(model, event)
            is Events.LogoutRequest -> logoutRequest(model)
            is Events.NavigateToRegisterRequest -> navigateToRegisterRequest(model)
            is Events.NavigateToLoginRequest -> navigateToLoginRequest(model)
            is Events.NavigateToHomeRequest -> navigateToHomeRequest(model)
            is Events.NavigateToDebugRequest -> navigateToDebugRequest(model)
            is Events.NavigateToElectionsRequest -> navigateToElectionsRequest(model)
            is Events.ListElectionsSuccess -> getElectionsSuccess(model, event)
            is Events.ListElectionsFailure -> getElectionsFailure(model, event)
            is Events.CreateElectionRequest -> createElectionRequest(model, event)
            is Events.CreateElectionSuccess -> createElectionSuccess(model)
            is Events.CreateElectionFailure -> createElectionFailure(model, event)
            is Events.NavigateToElectionRequest -> navigateToElectionRequest(model, event)
            is Events.NavigateToElectionSuccess -> navigateToElectionSuccess(model, event)
            is Events.NavigateToElectionFailure -> navigateToElectionFailure(model, event)
            is Events.DisplayException -> displayException(model, event)
            else -> unsupportedEvent(model, event)
        }
//        console.log("react.result.model", result.model)
//        console.log("react.result.effects", result.effects)
        return result
    }

    private fun initialize(model: Model): Result = Result(model, Effects.Render)

    private fun loginRequest(model: Model, event: Events.LoginRequest): Result {
        val effect = Effects.Login(event.nameOrEmail, event.password)
        val newLogin = model.login.copy(nameOrEmail = event.nameOrEmail, password = event.password)
        val newCredential = model.credential.copy(password = event.password)
        return Result(model.copy(credential = newCredential, login = newLogin), effect)
    }

    private fun loginSuccess(model: Model, event: Events.LoginSuccess): Result {
        val credential = model.credential.copy(name = event.name)
        val newModel = model.copy(credential = credential).homePage(name = event.name)
        return Result(newModel, Effects.Render)
    }

    private fun loginFailure(model: Model, event: Events.LoginFailure): Result =
            Result(model.withLoginError(event.reason), Effects.Render)

    private fun registerRequest(model: Model, event: Events.RegisterRequest): Result {
        val newRegisterModel = model.register.copy(
                name = event.name,
                email = event.email,
                password = event.password,
                confirmPassword = event.confirmPassword)
        val newModel = model.copy(register = newRegisterModel)
        return if (event.password == event.confirmPassword) {
            val effect = Effects.Register(event.name, event.email, event.password)
            Result(newModel, effect)
        } else {
            Result(newModel.withRegisterError("Password does not match confirmation password"), Effects.Render)
        }
    }

    private fun registerSuccess(model: Model, event: Events.RegisterSuccess) =
            Result(model.homePage(name = event.name), Effects.Render)

    private fun logoutRequest(model: Model) = Result(model.purgePasswords().loginPage(), Effects.Render)

    private fun registerFailure(model: Model, event: Events.RegisterFailure) = Result(model.withRegisterError(event.reason), Effects.Render)

    private fun navigateToLoginRequest(model: Model) = Result(model.loginPage(), Effects.Render)

    private fun navigateToRegisterRequest(model: Model) = Result(model.registerPage(), Effects.Render)

    private fun navigateToHomeRequest(model: Model) = Result(model.homePage(), Effects.Render)

    private fun navigateToDebugRequest(model: Model) = Result(model.debugPage(), Effects.Render)

    private fun navigateToElectionsRequest(model: Model): Result {
        val newModel = model.electionsPage()
        val listElectionsEffect = Effects.ListElections(model.credential)
        return Result(newModel, listElectionsEffect)
    }

    private fun getElectionsSuccess(model: Model, event: Events.ListElectionsSuccess): Result {
        val newModel = model.electionsPage().electionsList(event.elections)
        return Result(newModel, Effects.Render)
    }

    private fun getElectionsFailure(model: Model, event: Events.ListElectionsFailure): Result =
            Result(model.withElectionsError(event.reason), Effects.Render)

    private fun createElectionRequest(model: Model, event: Events.CreateElectionRequest): Result {
        val effect = Effects.CreateElection(model.credential, event.electionName)
        val newModel = model.createElectionPage()
        return Result(newModel, effect)
    }

    private fun createElectionSuccess(model: Model): Result {
        val effect = Effects.ListElections(model.credential)
        return Result(model, effect)
    }

    private fun createElectionFailure(model: Model, event: Events.CreateElectionFailure): Result {
        val newModel = model.withElectionsError(event.reason)
        val effect = Effects.Render
        return Result(newModel, effect)
    }

    private fun navigateToElectionRequest(model: Model, event: Events.NavigateToElectionRequest): Result {
        val effect = Effects.GetElection(event.electionName)
        return Result(model, effect)
    }

    private fun navigateToElectionSuccess(model: Model, event: Events.NavigateToElectionSuccess): Result {
        val newModel = model.goToElectionPage(event.election)
        return Result(newModel, Effects.Render)
    }

    private fun navigateToElectionFailure(model: Model, event: Events.NavigateToElectionFailure): Result {
        val newModel = model.withElectionsError(event.reason)
        return Result(newModel, Effects.Render)
    }

    private fun displayException(model: Model, event: Events.DisplayException) =
            Result(model.withUnsupportedError(error = "Unhandled exception ${event.ex.message}"), Effects.Render)

    private fun unsupportedEvent(model: Model, event: GenericEvent) =
            Result(model.withUnsupportedError(error = "Unsupported event: $event"), Effects.Render)
}
