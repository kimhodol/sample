package dev.hodol.sample.user

import dev.hodol.sample.entity.PrimaryKeyEntity
import dev.hodol.sample.order.Order
import dev.hodol.sample.user.vo.Email
import dev.hodol.sample.user.vo.Name
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "`user`")
@Where(clause = "deleted_at IS NOT NULL")
@SQLDelete(sql = "UPDATE `user` SET deleted_at = NOW() WHERE id = ?")
class User(
    name: Name,
    birthday: LocalDate,
    email: Email? = null,
) : PrimaryKeyEntity() {
    @Column(nullable = false, unique = true)
    var name: Name = name
        private set

    @Column(nullable = false)
    var birthday: LocalDate = birthday
        private set

    @Column(nullable = true)
    var email: Email? = email
        private set

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "orderer")
    private val mutableOrders: MutableList<Order> = mutableListOf()
    val orders: List<Order> get() = mutableOrders.toList()

    val deletedAt: LocalDateTime? = null

    fun addOrder(order: Order) {
        mutableOrders.add(order)
    }
}