package me.fungames.fortnite.api.model

import java.util.*

data class PartyInvite(
    val party_id: String,
    val sent_by: String,
    val meta: Map<String, String>,
    val sent_to: String,
    val sent_at: Date,
    val updated_at: Date,
    val expires_at: Date,
    val status: String
)
