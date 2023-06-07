package entity

data class Transaction (
    var to: String,
    var login: String,
    var amount: Long,
    var code: Int,
)
