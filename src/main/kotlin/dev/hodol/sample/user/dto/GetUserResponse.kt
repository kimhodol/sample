package dev.hodol.sample.user.dto

import dev.hodol.sample.user.User

data class GetUserResponse(
    val id: Long,
    val name: String,
    val age: Int,
    val email: String? = null,
) {
    companion object {
        fun of(user: User) = GetUserResponse(
            id = user.id!!,
            name = user.name,
            age = user.age,
            email = user.email
        )
    }
}