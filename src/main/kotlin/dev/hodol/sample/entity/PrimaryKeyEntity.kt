package dev.hodol.sample.entity

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*
import kotlin.jvm.Transient

// https://spoqa.github.io/2022/08/16/kotlin-jpa-entity.html
@MappedSuperclass
abstract class PrimaryKeyEntity : Persistable<UUID> {
    @Id
    @Column(columnDefinition = "uuid")
    private val id: UUID = UlidCreator.getMonotonicUlid().toUuid()

    @Transient
    private var _isNew = true

    override fun getId(): UUID = id

    override fun isNew(): Boolean = _isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other !is HibernateProxy && this::class != other::class) {
            return false
        }
        return id == getIdentifier(other)
    }

    override fun hashCode(): Int = id.hashCode()

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }

    private fun getIdentifier(other: Any): Serializable {
        return if (other is HibernateProxy) {
            (other.hibernateLazyInitializer.implementation as PrimaryKeyEntity).id
        } else {
            (other as PrimaryKeyEntity).id
        }
    }
}