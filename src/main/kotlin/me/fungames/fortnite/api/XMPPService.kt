@file:Suppress("UnstableApiUsage")

package me.fungames.fortnite.api

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import com.google.gson.Gson
import com.google.gson.JsonObject
import me.fungames.fortnite.api.events.Event
import me.fungames.fortnite.api.events.PartyEventListener
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

enum class NotificationType(insideParty: Boolean = true) {

    PING,
    MEMBER_LEFT,
    MEMBER_EXPIRED,
    MEMBER_NEW_CAPTAIN,
    MEMBER_KICKED,
    MEMBER_DISCONNECTED,
    PARTY_UPDATED,
    MEMBER_STATE_UPDATED,
    MEMBER_JOINED,
    MEMBER_REQUIRE_CONFIRMATION,
    INVITE_CANCELLED,
    INVITE_DECLINED,
    FRIENDSHIP_REMOVE(false),
    FRIENDSHIP_REQUEST(false),
    UNKNOWN(false);

    val id = if (insideParty) "com.epicgames.social.party.notification.v0.$name" else name

    companion object {
        fun from(str: String): NotificationType {
            for (value in values()) {
                if (value.id == str) {
                    return value
                }
            }
            return UNKNOWN
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

    val bus = EventBus().apply {
        register(this@XMPPService)
    }

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
        chat.addIncomingListener { from, message, chat ->
            bus.post(MessageReceivedEvent(from.localpart.asUnescapedString(), message.body))
        }
        roster = Roster.getInstanceFor(connection)
        bus.register(PartyEventListener(this))
    }

    override fun processStanza(packet: Stanza) {
        val msg = packet as Message
        val body = msg.bodyAsJson()
        if (body.has("type")) bus.post(NotificationReceivedEvent(NotificationType.from(body["type"].asString), msg))
    }

    fun sendMessage(accountId: String, msg: String) = chat.chatWith(JidCreate.entityBareFrom("$accountId:prod.ol.epicgames.com")).send(msg)

}

object DebugEventListener {
    @Subscribe fun onEvent(event: Event) {
        println(event)
    }
}