package dev.hodol.sample.order

import dev.hodol.sample.UsingTestcontainers
import dev.hodol.sample.user.User
import dev.hodol.sample.user.vo.Email
import dev.hodol.sample.user.vo.Name
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDate

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest(
    @Autowired em: TestEntityManager,
    @Autowired orderRepository: OrderRepository,
) : StringSpec({
    // https://github.com/kotest/kotest/issues/1643#issuecomment-686549912
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    "Orders should be saved with orderer" {
        val user = User(
            name = Name("WJ"),
            birthday = LocalDate.of(1994, 7, 12),
            email = Email("woonjangahn@gmail.com")
        )
        em.persist(user)

        val order = Order(user)
        val saved = orderRepository.save(order)

        em.flush()

        saved.orderer shouldBe user
        user.orders shouldContain order
    }
}), UsingTestcontainers