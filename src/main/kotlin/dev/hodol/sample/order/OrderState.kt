package dev.hodol.sample.order

enum class OrderState {
    PAYMENT_WAITING,
    PREPARING,
    SHIPPED,
    DELIVERING,
    DELIVERY_COMPLETED;

    fun next(): OrderState {
        return when (this) {
            PAYMENT_WAITING -> PREPARING
            PREPARING -> SHIPPED
            SHIPPED -> DELIVERING
            DELIVERING -> DELIVERY_COMPLETED
            DELIVERY_COMPLETED -> throw IllegalStateException("이미 배송완료되었습니다.")
        }
    }
}