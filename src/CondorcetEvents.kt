object CondorcetEvents {
    object Initialize : GenericEvent
    data class LoginRequest(val nameOrEmail: String, val password: String) : GenericEvent
    data class LoginSuccess(val name: String) : GenericEvent
    data class LoginFailure(val reason: String) : GenericEvent
    data class RegisterRequest(val name: String, val email: String, val password: String, val confirmPassword: String) : GenericEvent
    data class RegisterSuccess(val name: String) : GenericEvent
    data class RegisterFailure(val reason: String) : GenericEvent
    object LogoutRequest : GenericEvent
    object NavigateToRegisterRequest : GenericEvent
    object NavigateToLoginRequest : GenericEvent
    object NavigateToHomeRequest : GenericEvent
}
