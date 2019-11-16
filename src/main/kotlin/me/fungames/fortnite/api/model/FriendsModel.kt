package me.fungames.fortnite.api.model

import java.util.*

data class Friend(
    val accountId: String,
    val status: String,
    val direction: String,
    val created: Date,
    val favorite: Boolean
)

data class BlockedUsers(
    val blockedUsers: List<Friend>
)

data class FriendsSettings(
    var acceptInvites: String
)