package dev.hodol.sample.order

import dev.hodol.sample.entity.PrimaryKeyEntity
import dev.hodol.sample.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "`order`")
class Order(
    orderer: User,
    orderedAt: LocalDateTime = LocalDateTime.now(),
    state: OrderState = OrderState.PAYMENT_WAITING
) : PrimaryKeyEntity() {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var orderer: User = orderer
        private set

    @Column
    var orderedAt: LocalDateTime = orderedAt
        private set

    @Enumerated
    @Column
    var state: OrderState = state
        private set

    fun progress() {
        state = state.next()
    }

    init {
        orderer.addOrder(this)
    }
}