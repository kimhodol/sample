package dev.hodol.sample.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getUserById(userId: Long): User {
        return userRepository.findByIdOrNull(userId)
            ?: throw IllegalStateException("유저가 존재하지 않습니다.")
    }
}
