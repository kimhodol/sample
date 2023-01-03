package dev.hodol.sample.user

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val age: Int,

    @Column(nullable = true)
    val email: String? = null,

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,
)