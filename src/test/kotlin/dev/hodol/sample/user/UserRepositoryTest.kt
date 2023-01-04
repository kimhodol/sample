package dev.hodol.sample.user

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UserRepositoryTest(
    @Autowired
    val userRepository: UserRepository
) : BehaviorSpec({
    given("유저 객체를 생성하고") {
        val user = User("WJ", 30)
        `when`("save를 호출하면") {
            val saved = userRepository.save(user)
            then("유저 객체가 저장된다.") {
                saved.id shouldBe 1L
                saved.name shouldBe "WJ"
                saved.email shouldBe null
            }
        }
    }
}) {
    companion object {
        @JvmStatic
        @Container
        private val container = PostgreSQLContainer("postgres:latest")
            .withDatabaseName("testdb")
    }
}