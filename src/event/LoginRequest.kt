package event

import generic.GenericEvent

data class LoginRequest(val nameOrEmail:String, val password:String): GenericEvent