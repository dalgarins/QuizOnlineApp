package co.anbora.onlinequizapp.domain.model

data class User(val userName: String, val password: String, val email: String) {
    constructor(): this(userName="", password = "", email = "")
}