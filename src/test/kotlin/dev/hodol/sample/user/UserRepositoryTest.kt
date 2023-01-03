package dev.hodol.sample.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `유저를 생성한다`() {
        val user = User(
            "WJ",
            30,
        )
        val saved = userRepository.save(user)

        assertThat(saved.id).isEqualTo(1L)
        assertThat(saved.name).isEqualTo("WJ")
        assertThat(saved.email).isNull()
    }

    companion object {
        @JvmStatic
        @Container
        private val container = PostgreSQLContainer("postgres:latest")
            .withDatabaseName("testdb")
    }
}