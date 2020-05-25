@file:Suppress("UnstableApiUsage")

package me.fungames.fortnite.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.fungames.fortnite.api.events.Event
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

abstract class JsonEvent(val jsonObject: JsonObject): Event

open class PartyEvent(jsonObject: JsonObject): JsonEvent(jsonObject) {
    val partyId: String = jsonObject["party_id"].asString!!
    override fun toString(): String {
        return "PartyEvent(partyId='$partyId')"
    }
}

class PartyMemberEvent(jsonObject: JsonObject): PartyEvent(jsonObject) {
    val accountId: String = jsonObject["account_id"].asString!!
    override fun toString(): String {
        return "PartyMemberEvent(accountId='$accountId') ${super.toString()}"
    }
}

class InviteChangeEvent(jsonObject: JsonObject): JsonEvent(jsonObject) {
    val inviteeId: String = jsonObject["invitee_id"].asString!!
    override fun toString(): String {
        return "InviteChangeEvent(inviteeId='$inviteeId')"
    }
}

class PingEvent(jsonObject: JsonObject): JsonEvent(jsonObject) {
    val pingerId: String = jsonObject["pinger_id"].asString!!
    override fun toString(): String {
        return "PingEvent(pingerId='$pingerId')"
    }
}

class FriendEvent(jsonObject: JsonObject): JsonEvent(jsonObject) {
    val to: String? = jsonObject["to"].asString
    val from: String? = jsonObject["from"].asString
    val reason: String? = jsonObject["reason"].asString
    val status: String? = jsonObject["status"].asString
    override fun toString(): String {
        return "FriendEvent(to=$to, from=$from, reason=$reason, status=$status)"
    }
}

enum class NotificationType(insideParty: Boolean = true, val type: Class<out JsonEvent> = PartyMemberEvent::class.java) {

    PING(type = PingEvent::class.java),
    MEMBER_LEFT,
    MEMBER_EXPIRED,
    MEMBER_NEW_CAPTAIN,
    MEMBER_KICKED,
    MEMBER_DISCONNECTED,
    PARTY_UPDATED(type = PartyEvent::class.java),
    MEMBER_STATE_UPDATED,
    MEMBER_JOINED,
    MEMBER_REQUIRE_CONFIRMATION,
    INVITE_CANCELLED(type = InviteChangeEvent::class.java),
    INVITE_DECLINED(type = InviteChangeEvent::class.java),
    FRIENDSHIP_REMOVE(false, FriendEvent::class.java),
    FRIENDSHIP_REQUEST(false, FriendEvent::class.java),
    UNKNOWN(false);

    val id = if (insideParty) "com.epicgames.social.party.notification.v0.$name" else name

    private val listeners: MutableList<JsonEvent.() -> Unit> = mutableListOf()

    @Suppress("UNCHECKED_CAST")
    fun <T: JsonEvent> listen(block: T.() -> Unit) {
        listeners.add {
            block(this as T)
        }
    }

    internal fun fire(obj: JsonObject) {
        listeners.forEach {
            it(this.type.getConstructor(JsonEvent::class.java).newInstance(obj))
        }
    }

    companion object {
        fun from(str: String): NotificationType {
            for (value in values()) {
                if (value.id == str) {
                    return value
                }
            }
            return UNKNOWN
        }
        fun listenToAll(block: JsonEvent.() -> Unit) {
            values().forEach {
                it.listen<JsonEvent> {
                    block(this)
                }
            }
        }
    }

}

fun Message.bodyAsJson(): JsonObject = Gson().fromJson(this.body, JsonObject::class.java)

data class MessageReceivedEvent(val accountId: String, val msg: String): Event
data class NotificationReceivedEvent(val type: NotificationType, val msg: Message): Event

class XMPPService(val api: FortniteApi): StanzaListener {

    private val connection: AbstractXMPPConnection
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

    override fun processStanza(packet: Stanza) {
        val msg = packet as Message
        val body = msg.bodyAsJson()
        if (body.has("type")) NotificationType.from(body["type"].asString).fire(body)
    }

    fun sendMessage(accountId: String, msg: String) = chat.chatWith(JidCreate.entityBareFrom("$accountId:prod.ol.epicgames.com")).send(msg)

}