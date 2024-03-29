package com.bottmac.bottmac.models

import java.time.LocalDateTime

data class NewUser(
    val userUID: String,
    val userName: String,
    val userCreatedAt: LocalDateTime,
    val userModifiedAt: LocalDateTime,
)