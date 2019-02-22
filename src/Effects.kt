import kotlin.dom.clear

object Effects {
    object Render : Effect {
        override fun apply(model: Model,
                           api: Api,
                           environment: Environment,
                           components: GenericComponents,
                           handleEvent: (GenericEvent) -> Unit) {
            try {
                val body = environment.document.body!!
                body.clear()
                val page = model.page
                val component = components[page]
                val (rendered, focused) = component.render(model, handleEvent)
                body.appendChild(rendered)
                focused?.focus()
            } catch (ex: Throwable) {
                handleEvent(Events.DisplayException(ex))
            }
        }
    }

    class Login(private val nameOrEmail: String, private val password: String) : Effect {
        override fun apply(model: Model, api: Api, environment: Environment, components: GenericComponents, handleEvent: (GenericEvent) -> Unit) {
            val promise = api.login(nameOrEmail, password)
            promise.then { loginResponse ->
                handleEvent(Events.LoginSuccess(loginResponse.name))
            }.catch { throwable ->
                handleEvent(Events.LoginFailure(throwable.message))
            }
        }
    }

    class Register(private val name: String, private val email: String, private val password: String) : Effect {
        override fun apply(model: Model, api: Api, environment: Environment, components: GenericComponents, handleEvent: (GenericEvent) -> Unit) {
            api.register(name, email, password).then { registerResponse ->
                handleEvent(Events.RegisterSuccess(registerResponse.name))
            }.catch { throwable ->
                handleEvent(Events.RegisterFailure(throwable.message))
            }
        }
    }

    class ListElections(private val credential: Api.Credential) : Effect {
        override fun apply(model: Model, api: Api, environment: Environment, components: GenericComponents, handleEvent: (GenericEvent) -> Unit) {
            api.listElections(credential).then { listElectionsResponse ->
                handleEvent(Events.ListElectionsSuccess(listElectionsResponse.elections))
            }.catch { throwable ->
                handleEvent(Events.ListElectionsFailure(throwable.message))
            }
        }
    }

    class CreateElection(private val credential: Api.Credential, private val electionName: String) : Effect {
        override fun apply(model: Model, api: Api, environment: Environment, components: GenericComponents, handleEvent: (GenericEvent) -> Unit) {
            api.createElection(credential, electionName).then { createElectionResponse ->
                handleEvent(Events.CreateElectionSuccess(createElectionResponse.election))
            }.catch { throwable ->
                handleEvent(Events.CreateElectionFailure(throwable.message))
            }
        }
    }

    class GetElection(private val electionName: String) : Effect {
        override fun apply(model: Model, api: Api, environment: Environment, components: GenericComponents, handleEvent: (GenericEvent) -> Unit) {
            api.getElection(model.credential, electionName).then { getElectionResponse ->
                handleEvent(Events.NavigateToElectionSuccess(getElectionResponse.election))
            }.catch { throwable ->
                handleEvent(Events.NavigateToElectionFailure(throwable.message))
            }
        }
    }
}
