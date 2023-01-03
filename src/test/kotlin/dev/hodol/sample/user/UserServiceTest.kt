package dev.hodol.sample.user

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@ExtendWith(MockKExtension::class)
class UserServiceTest {
    @MockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var userService: UserService

    @Test
    fun `유저 목록을 조회한다`() {
        val expected = listOf(
            User("WJ", 30, null, 1L),
            User("Sangwoo", 29, "sangw0804@naver.com", 2L)
        )
        every { userRepository.findAll() } returns expected
        val actual = userService.getUsers()
        assertThat(actual).containsAll(expected)
    }

    @Test
    fun `유저가 존재하지 않는 경우 예외를 발생한다`() {
        every { userRepository.findByIdOrNull(any()) } returns null
        assertThatThrownBy {
            userService.getUserById(1L)
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessage("유저가 존재하지 않습니다.")
    }
}