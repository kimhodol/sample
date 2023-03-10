package dev.hodol.sample.user.vo

@JvmInline
value class Email(val value: String) {
    init {
        require(value.split("@").size == 2) { "올바르지 않은 이메일 형식입니다." }
    }
}