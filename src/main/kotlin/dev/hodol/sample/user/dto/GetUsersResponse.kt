package dev.hodol.sample.user.dto

import dev.hodol.sample.user.User

data class GetUsersResponse(
    val users: List<GetUserResponse>
) {
    companion object {
        fun of(users: List<User>) = GetUsersResponse(users.map { GetUserResponse.of(it) })
    }
}
