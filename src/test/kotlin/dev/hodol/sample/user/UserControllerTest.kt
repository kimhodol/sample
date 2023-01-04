package dev.hodol.sample.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dev.hodol.sample.user.dto.GetUserResponse
import dev.hodol.sample.user.dto.GetUsersResponse
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(UserController::class)
class UserControllerTest(
    @Autowired
    private val mvc: MockMvc,
    @MockkBean
    private val userService: UserService,
    private val mapper: ObjectMapper = jacksonObjectMapper(),
) : StringSpec({
    "유저 목록을 조회한다." {
        val users = listOf(
            User("WJ", 30, null, 1L),
            User("Sangwoo", 29, "sangw0804@naver.com", 2L)
        )
        every { userService.getUsers() } returns users

        val expected = GetUsersResponse.of(users)

        mvc.get("/users") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { mapper.writeValueAsString(expected) }
        }.andDo {
            print()
        }
    }

    "유저를 조회한다." {
        val user = User("WJ", 30, null, 1L)
        every { userService.getUserById(any()) } returns user

        val expected = GetUserResponse.of(user)

        mvc.get("/users/1") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { mapper.writeValueAsString(expected) }
        }.andDo {
            print()
        }
    }
})