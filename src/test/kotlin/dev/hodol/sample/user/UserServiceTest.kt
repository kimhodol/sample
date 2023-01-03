package dev.hodol.sample.user

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var userService: UserService

    @Test
    fun `유저 목록을 조회한다`() {
        val expected = listOf(
            User("WJ", 30, null, 1L),
            User("Sangwoo", 29, "sangw0804@naver.com", 2L)
        )
        given(userRepository.findAll()).willReturn(expected)
        val actual = userService.getUsers()
        assertThat(actual).containsAll(expected)
    }

    @Test
    fun `유저가 존재하지 않는 경우 예외를 발생한다`() {
        given(userRepository.findById(any())).willReturn(Optional.empty())
        assertThatThrownBy {
            userService.getUserById(1L)
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessage("유저가 존재하지 않습니다.")
    }
}