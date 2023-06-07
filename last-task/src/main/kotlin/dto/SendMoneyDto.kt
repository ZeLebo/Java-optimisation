package dto

import kotlinx.serialization.Serializable

@Serializable
data class SendMoneyDto(var salt: String)