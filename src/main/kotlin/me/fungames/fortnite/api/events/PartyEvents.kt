@file:Suppress("UnstableApiUsage")

package me.fungames.fortnite.api.events

import com.google.common.eventbus.Subscribe
import com.google.gson.Gson
import com.google.gson.JsonObject
import me.fungames.fortnite.api.NotificationReceivedEvent
import me.fungames.fortnite.api.NotificationType
import me.fungames.fortnite.api.XMPPService
import me.fungames.fortnite.api.bodyAsJson

class PingNotificationEvent(val pingerId: String): Event
abstract class PartyEvent(val partyId: String): Event {
    constructor(jsonObject: JsonObject): this(jsonObject["party_id"].asString)
}
abstract class PartyMemberEvent(jsonObject: JsonObject): PartyEvent(jsonObject) {
    val accountId = jsonObject["account_id"].asString!!
}
class PartyMemberLeftEvent(jsonObject: JsonObject): PartyMemberEvent(jsonObject)
class PartyMemberExpiredEvent(jsonObject: JsonObject): PartyMemberEvent(jsonObject)
class PartyNewCaptainEvent(jsonObject: JsonObject): PartyMemberEvent(jsonObject)
class PartyMemberKickedEvent(jsonObject: JsonObject): PartyMemberEvent(jsonObject)
class PartyMemberDisconnectedEvent(jsonObject: JsonObject): PartyMemberEvent(jsonObject)
class PartyUpdatedEvent(jsonObject: JsonObject): PartyEvent(jsonObject)
class PartyMemberStateUpdatedEvent(jsonObject: JsonObject): PartyMemberEvent(jsonObject)
class PartyMemberJoinedEvent(jsonObject: JsonObject): PartyMemberEvent(jsonObject)
class PartyMemberRequireConfirmationEvent(jsonObject: JsonObject): PartyMemberEvent(jsonObject)
abstract class InviteChangeEvent(jsonObject: JsonObject): PartyEvent(jsonObject) {
    val inviteeId = jsonObject["invitee_id"].asString!!
}
class InviteCancelledEvent(jsonObject: JsonObject): InviteChangeEvent(jsonObject)
class InviteDeclinedEvent(jsonObject: JsonObject): InviteChangeEvent(jsonObject)
class FriendEvent(val to: String?,
                  val from: String?,
                  val reason: String?,
                  val status: String?)
class PartyEventListener(val service: XMPPService) {

    private fun post(any: Any) = service.bus.post(any)

    @Subscribe fun onNotificationReceived(noti: NotificationReceivedEvent) {
        val body = noti.msg.bodyAsJson()
        when (noti.type) {
            NotificationType.PING -> post(PingNotificationEvent(body["pinger_id"].asString))
            NotificationType.FRIENDSHIP_REMOVE -> post(Gson().fromJson(body, FriendEvent::class.java))
            NotificationType.FRIENDSHIP_REQUEST -> post(Gson().fromJson(body, FriendEvent::class.java))
            NotificationType.MEMBER_LEFT -> post(PartyMemberLeftEvent(body))
            NotificationType.MEMBER_EXPIRED -> post(PartyMemberExpiredEvent(body))
            NotificationType.MEMBER_NEW_CAPTAIN -> post(PartyNewCaptainEvent(body))
            NotificationType.MEMBER_KICKED -> post(PartyMemberKickedEvent(body))
            NotificationType.MEMBER_DISCONNECTED -> post(PartyMemberDisconnectedEvent(body))
            NotificationType.PARTY_UPDATED -> post(PartyUpdatedEvent(body))
            NotificationType.MEMBER_STATE_UPDATED -> post(PartyMemberStateUpdatedEvent(body))
            NotificationType.MEMBER_JOINED -> post(PartyMemberJoinedEvent(body))
            NotificationType.MEMBER_REQUIRE_CONFIRMATION -> post(PartyMemberRequireConfirmationEvent(body))
            NotificationType.INVITE_CANCELLED -> post(InviteCancelledEvent(body))
            NotificationType.INVITE_DECLINED -> post(InviteDeclinedEvent(body))
            else -> {
                println("Unhandled notification type received")
            }
        }
    }

}