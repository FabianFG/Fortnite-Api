@file:Suppress("UnstableApiUsage", "UNCHECKED_CAST")

package me.fungames.fortnite.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.filter.MessageTypeFilter
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Stanza
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.ping.PingManager
import org.jxmpp.jid.impl.JidCreate
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

open class PartyEvent(@SerializedName("party_id") var partyId: String = "") {
    override fun toString(): String {
        return "PartyEvent(partyId='$partyId')"
    }
}

data class PartyMemberEvent(@SerializedName("account_id") var accountId: String = ""): PartyEvent()
data class InviteChangeEvent(@SerializedName("invitee_id") var inviteeId: String = "")
data class PingEvent(@SerializedName("pinger_id") var pingerId: String = "")
data class FriendEvent(val to: String?,
                       val from: String?,
                       val reason: String?,
                       val status: String?,
                       val timestamp: String)

sealed class NotificationType<T: Any>(val clazz: KClass<T>) {

    object PING: NotificationType<PingEvent>(PingEvent::class)
    object MEMBER_LEFT: NotificationType<PartyMemberEvent>(PartyMemberEvent::class)
    object MEMBER_EXPIRED: NotificationType<PartyMemberEvent>(PartyMemberEvent::class)
    object MEMBER_NEW_CAPTAIN: NotificationType<PartyMemberEvent>(PartyMemberEvent::class)
    object MEMBER_KICKED: NotificationType<PartyMemberEvent>(PartyMemberEvent::class)
    object MEMBER_DISCONNECTED: NotificationType<PartyMemberEvent>(PartyMemberEvent::class)
    object PARTY_UPDATED: NotificationType<PartyEvent>(PartyEvent::class)
    object MEMBER_STATE_UPDATED: NotificationType<PartyMemberEvent>(PartyMemberEvent::class)
    object MEMBER_JOINED: NotificationType<PartyMemberEvent>(PartyMemberEvent::class)
    object MEMBER_REQUIRE_CONFIRMATION: NotificationType<PartyMemberEvent>(PartyMemberEvent::class)
    object INVITE_CANCELLED: NotificationType<InviteChangeEvent>(InviteChangeEvent::class)
    object INVITE_DECLINED: NotificationType<InviteChangeEvent>(InviteChangeEvent::class)
    object FRIENDSHIP_REMOVE: NotificationType<FriendEvent>(FriendEvent::class)
    object FRIENDSHIP_REQUEST: NotificationType<FriendEvent>(FriendEvent::class)
    object UNKNOWN: NotificationType<JsonObject>(JsonObject::class)

    val name = if (clazz.isSubclassOf(PartyEvent::class)) "com.epicgames.social.party" +
            ".notification.v0.${this::class.simpleName}" else this::class.simpleName

    companion object {
        fun values(): List<NotificationType<*>> = NotificationType::class.sealedSubclasses.mapNotNull { it.objectInstance }
        fun from(str: String): NotificationType<*> {
            for (value in values()) {
                if (value.name == str) return value
            }
            return UNKNOWN
        }
    }

}

fun Message.bodyAsJson(): JsonObject = Gson().fromJson(this.body, JsonObject::class.java)

class XMPPService(val api: FortniteApi): StanzaListener {

    internal val connection: AbstractXMPPConnection
    val chat: ChatManager
    val roster: Roster

    init {
        connection = XMPPTCPConnection(XMPPTCPConnectionConfiguration
                .builder()
                .setXmppDomain("prod.ol.epicgames.com")
                .setHost("xmpp-service-prod.ol.epicgames.com")
                .setPort(5222)
                .setConnectTimeout(60000)
                .setResource("V2:Fortnite:WIN::${UUID.randomUUID().toString().replace("-", "")}")
                .build())
        connection.replyTimeout = 60000
        connection.addAsyncStanzaListener(this, MessageTypeFilter.NORMAL)
        connection.connect().login(api.accountId, api.accountToken)
        PingManager.getInstanceFor(connection).pingInterval = 60
        chat = ChatManager.getInstanceFor(connection)
        roster = Roster.getInstanceFor(connection)
    }

    private val listeners: MutableMap<NotificationType<*>, MutableList<Any.() -> Unit>> = mutableMapOf()

    fun <T: Any> listen(type: NotificationType<T>, block: T.() -> Unit) {
        if (!listeners.containsKey(type)) {
            listeners[type] = mutableListOf()
        }
        listeners[type]!!.add {
            block(this as T)
        }
    }

    private fun <T: Any> fire(type: NotificationType<T>, str: String) {
        listeners.forEach {
            if (it.key == type) {
                val body = Gson().fromJson(str, type.clazz.java)
                it.value.forEach {
                    it(body as Any)
                }
            }
        }
    }

    override fun processStanza(packet: Stanza) {
        val msg = packet as Message
        val body = msg.bodyAsJson()
        if (body.has("type")) fire(NotificationType.from(body["type"].asString), msg.body)
    }

    fun sendMessage(accountId: String, msg: String) = chat.chatWith(JidCreate.entityBareFrom("$accountId:prod.ol.epicgames.com")).send(msg)

}