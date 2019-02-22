object Events {
    object Initialize : GenericEvent {
        override fun toString(): String = "Initialize"
    }
    data class LoginRequest(val nameOrEmail: String, val password: String) : GenericEvent
    data class LoginSuccess(val name: String) : GenericEvent
    data class LoginFailure(val reason: String?) : GenericEvent
    data class RegisterRequest(val name: String, val email: String, val password: String, val confirmPassword: String) : GenericEvent
    data class RegisterSuccess(val name: String) : GenericEvent
    data class RegisterFailure(val reason: String?) : GenericEvent
    data class CreateElectionRequest(val electionName: String) : GenericEvent
    data class CreateElectionSuccess(val election: Api.Election) : GenericEvent
    data class CreateElectionFailure(val reason: String?) : GenericEvent
    data class ListElectionsSuccess(val elections: List<Api.Election>) : GenericEvent
    data class ListElectionsFailure(val reason: String?) : GenericEvent
    object LogoutRequest : GenericEvent {
        override fun toString(): String = "LogoutRequest"
    }

    object NavigateToRegisterRequest : GenericEvent {
        override fun toString(): String = "NavigateToRegisterRequest"
    }

    object NavigateToLoginRequest : GenericEvent {
        override fun toString(): String = "NavigateToLoginRequest"
    }

    object NavigateToHomeRequest : GenericEvent {
        override fun toString(): String = "NavigateToHomeRequest"
    }

    object NavigateToDebugRequest : GenericEvent {
        override fun toString(): String = "NavigateToDebugRequest"
    }

    object NavigateToElectionsRequest : GenericEvent {
        override fun toString(): String = "NavigateToElectionsRequest"
    }

    object NavigateToVotesRequest : GenericEvent {
        override fun toString(): String = "NavigateToVotesRequest"
    }

    data class NavigateToElectionRequest(val electionName: String) : GenericEvent
    data class NavigateToElectionSuccess(val election: Api.Election) : GenericEvent
    data class NavigateToElectionFailure(val reason: String?) : GenericEvent
    data class DisplayException(val ex: Throwable) : GenericEvent
}
