package dev.hodol.sample.user

import dev.hodol.sample.UsingTestcontainers
import dev.hodol.sample.user.vo.Email
import dev.hodol.sample.user.vo.Name
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest(
    @Autowired val userRepository: UserRepository
) : BehaviorSpec({
    given("유저 객체를 생성하고") {
        val user = User(
            name = Name("WJ"),
            birthday = LocalDate.of(1994, 7, 12),
            email = Email("woonjangahn@gmail.com")
        )
        `when`("save를 호출하면") {
            val saved = userRepository.save(user)
            then("유저 객체가 저장된다.") {
                saved.name shouldBe "WJ"
                saved.email shouldBe null
            }
        }
    }
}), UsingTestcontainers