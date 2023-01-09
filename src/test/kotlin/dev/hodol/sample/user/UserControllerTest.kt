package dev.hodol.sample.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dev.hodol.sample.user.dto.GetUserResponse
import dev.hodol.sample.user.dto.GetUsersResponse
import dev.hodol.sample.user.vo.Email
import dev.hodol.sample.user.vo.Name
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDate

@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(UserController::class)
class UserControllerTest(
    @Autowired
    private val context: WebApplicationContext,
    @MockkBean
    private val userService: UserService,
) : StringSpec({
    val restDocumentation = ManualRestDocumentation()
    val mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply<DefaultMockMvcBuilder>(
            documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint())
        )
        .build()
    val mapper = jacksonObjectMapper()

    beforeEach {
        restDocumentation.beforeTest(javaClass, it.name.testName)
    }

    afterEach {
        restDocumentation.afterTest()
    }

    "유저 목록을 조회한다." {
        val users = listOf(
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
            handle(
                document(
                    "getUsers", responseFields(
                        fieldWithPath("users[].id").type(JsonFieldType.NUMBER).description("회원 ID"),
                        fieldWithPath("users[].name").type(JsonFieldType.STRING).description("회원의 이름"),
                        fieldWithPath("users[].email").type(JsonFieldType.STRING).description("회원의 이메일").optional(),
                        fieldWithPath("users[].age").type(JsonFieldType.NUMBER).description("회원의 나이")
                    )
                )
            )
        }
    }

    "단일 유저를 조회한다." {
        val user = User(
            name = Name("WJ"),
            birthday = LocalDate.of(1994, 7, 12),
            email = Email("woonjangahn@gmail.com")
        )

        every { userService.getUserById(any()) } returns user

        val expected = GetUserResponse.of(user)

        mvc.perform(
            RestDocumentationRequestBuilders.get("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect {
            status().isOk
            content().contentType(MediaType.APPLICATION_JSON)
            content().json(mapper.writeValueAsString(expected))
        }.andDo(
            document(
                "getUser",
                pathParameters(
                    parameterWithName("id").description("회원 ID")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 ID"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("회원의 이름"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("회원의 이메일").optional(),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("회원의 나이")
                )
            )
        )
    }
})