package dev.hodol.sample.user

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull

class UserServiceTest : DescribeSpec({
    val userRepository = mockk<UserRepository>()
    val userService = UserService(userRepository)

    describe("UserService 테스트") {
        it("유저 목록을 조회한다.") {
            val expected = listOf(
                User("WJ", 30, null, 1L),
                User("Sangwoo", 29, "sangw0804@naver.com", 2L)
            )
            every { userRepository.findAll() } returns expected
            val actual = userService.getUsers()
            actual shouldContainAll expected
        }

        it("유저가 존재하지 않는 경우 예외를 발생한다.") {
            every { userRepository.findByIdOrNull(any()) } returns null
            val exception = shouldThrowExactly<IllegalStateException> {
                userService.getUserById(1L)
            }
            exception.message shouldBe "유저가 존재하지 않습니다."
        }
    }
})