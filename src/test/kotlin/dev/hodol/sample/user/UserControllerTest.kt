package dev.hodol.sample.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.hodol.sample.user.dto.GetUserResponse
import dev.hodol.sample.user.dto.GetUsersResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(UserController::class)
class UserControllerTest {
    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var userService: UserService

    private val mapper = jacksonObjectMapper()

    @Test
    fun `유저 목록을 조회한다`() {
        val users = listOf(
            User("WJ", 30, null, 1L),
            User("Sangwoo", 29, "sangw0804@naver.com", 2L)
        )
        given(userService.getUsers()).willReturn(users)

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

    @Test
    fun `유저를 조회한다`() {
        val user = User("WJ", 30, null, 1L)
        given(userService.getUserById(anyLong())).willReturn(user)

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
}