package dev.hodol.sample.user

import dev.hodol.sample.user.vo.Email
import dev.hodol.sample.user.vo.Name
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate
import java.util.*

class UserServiceTest : DescribeSpec({
    val userRepository = mockk<UserRepository>()
    val userService = UserService(userRepository)

    describe("UserService 테스트") {
        it("유저 목록을 조회한다.") {
            val expected = listOf(
                User(
                    name = Name("WJ"),
                    birthday = LocalDate.of(1994, 7, 12),
                    email = Email("woonjangahn@gmail.com")
                ),
                User(
                    name = Name("Sangwoo"),
                    birthday = LocalDate.of(1995, 8, 4),
                ),
            )
            every { userRepository.findAll() } returns expected
            val actual = userService.getUsers()
            actual shouldContainAll expected
        }

        it("유저가 존재하지 않는 경우 예외를 발생한다.") {
            every { userRepository.findByIdOrNull(any()) } returns null
            val exception = shouldThrowExactly<IllegalStateException> {
                userService.getUserById(UUID(0, 0))
            }
            exception.message shouldBe "유저가 존재하지 않습니다."
        }
    }
})