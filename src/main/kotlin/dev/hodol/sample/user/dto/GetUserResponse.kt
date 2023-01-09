package dev.hodol.sample.user.dto

import dev.hodol.sample.user.User
import dev.hodol.sample.user.vo.Email
import dev.hodol.sample.user.vo.Name
import java.time.LocalDate
import java.util.*

data class GetUserResponse(
    val id: UUID,
    val name: Name,
    val birthday: LocalDate,
    val email: Email? = null,
) {
    companion object {
        fun of(user: User) = GetUserResponse(
            id = user.id,
            name = user.name,
            birthday = user.birthday,
            email = user.email
        )
    }
}