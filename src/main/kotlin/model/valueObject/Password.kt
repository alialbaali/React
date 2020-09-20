package com.shopping.common.domain.model.valueObject

inline class Password(private val value: String) {

    companion object {

        private val Regex = Regex("^(?=.*?[0-9]).{8,}$")

        fun create(password: String, hash: String.() -> String): Result<Password> =
            if (password.matches(Regex)) {
                val hashedPassword = password.hash()
                Result.success(Password(hashedPassword))
            } else {
                Result.failure(Throwable())
            }
    }

    override fun toString(): String = value
}
