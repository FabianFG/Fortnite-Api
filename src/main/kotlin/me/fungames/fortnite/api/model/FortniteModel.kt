package me.fungames.fortnite.api.model

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.fungames.fortnite.api.model.notification.ProfileNotification
import java.util.*
import java.util.AbstractMap.SimpleEntry

/**
 * As seen in the BR profile items' <tt>attributes</tt> object. Used to transfer variant data from/to the backend.
 */
data class Variant(
    var channel: String,
    var active: String,
    var owned: List<String>
)

enum class EMtxPlatform {
    WeGame, EpicPCKorea, Epic, EpicPC, EpicAndroid, PSN, Live, IOSAppStore, Nintendo, Samsung, Shared
}

enum class ECustomizationSlot {
    Backpack, VictoryPose, LoadingScreen, Character, Glider, Dance, CallingCard, ConsumableEmote, MapMarker, SkyDiveContrail, Hat, PetSkin, ItemWrap, MusicPack, BattleBus, Pickaxe, VehicleDecoration
}

data class LootResult(
    var itemType: String,
    var itemGuid: String,
    var itemProfile: String,
    var attributes: JsonObject,
    var quantity: Int,
    @field:Transient
    private var stack: FortItemStack? = null
) {
    fun asItemStack(): FortItemStack {
        if (stack == null) {
            stack = FortItemStack(itemType, quantity)
        }
        return stack!!
    }
}

data class FortMcpResponse(
    val profileRevision: Int,
    val profileId: String,
    val profileChangesBaseRevision: Int,
    val profileChanges: List<JsonObject>,
    val notifications: List<ProfileNotification>,
    val profileCommandRevision: Int,
    val serverTime: Date,
    val multiUpdate: List<FortMcpResponse>,
    val responseVersion: String
)

enum class ECurrencyType {
    Other, RealMoney, GameItem, MtxCurrency
}

data class FortCatalogResponse(
    val refreshIntervalHrs: Int,
    val dailyPurchaseHrs: Int,
    val expiration: Date,
    val storefronts: List<Storefront>
) {
    data class Storefront(
        var name: String,
        var catalogEntries: List<CatalogEntry>
    )

    data class CatalogEntry(
        //***CurrencyStorefront***
        var offerId: String,
        var devName: String,
        var offerType: ECatalogOfferType,
        var prices: List<Price>,
        var categories: List<String>,
        var dailyLimit: Int,
        var weeklyLimit: Int,
        var monthlyLimit: Int,
        var appStoreId: List<String>,
        var requirements: List<Requirement>,
        var metaInfo: List<SimpleEntry<String, String>>,
        var catalogGroup: String,
        var catalogGroupPriority: Int,
        var sortPriority: Int,
        var title: String,
        var shortDescription: String,
        var description: String,
        var displayAssetPath: String,
        var itemGrants: List<FortItemStack>,

        //***BRWeeklyStorefront***
        var fulfillmentIds: List<String>,
        var dynamicBundleInfo: DynamicBundleInfo,
        // ex: "meta":{
        //     "BannerOverride":"NewStyle"
        //     "StoreToastHeader":"Updated",
        //     "StoreToastBody":"ItemIsBack"
        // }
        var meta: JsonObject,
        var matchFilter: String,
        var filterWeight: Float,
        var giftInfo: GiftInfo,
        var refundable: Boolean,
        // ex: com.epicgames.fortnite.core.game.fulfillments.BattlePassTierFulfillment
        var fulfillmentClass: String
    )

    data class Price(
        var currencyType: ECurrencyType,
        var currencySubType: String,
        var regularPrice: Int,
        var finalPrice: Int,
        //nonexistent if not sale
        var saleType: ECatalogSaleType? = null,
        var saleExpiration: Date? = null,
        var basePrice: Int? = null
    ) {
        companion object {
            val NO_PRICE: Price = Price(
                ECurrencyType.MtxCurrency, "", -1, -1, basePrice = -1
            )
        }
    }

    data class Requirement(
        val requirementType: ECatalogRequirementType,
        val requiredId: String,
        val minQuantity: Int
    )

    data class DynamicBundleInfo(
        val discountedBasePrice: Int,
        val regularBasePrice: Int,
        val currencyType: ECurrencyType,
        val currencySubType: String,
        val displayType: ECatalogSaleType,
        val bundleItems: List<BundleItem>
    )

    data class BundleItem(
        val bCanOwnMultiple: Boolean,
        val regularPrice: Int,
        val discountedPrice: Int,
        val alreadyOwnedPriceReduction: Int,
        val item: FortItemStack
    )

    data class GiftInfo(
        val bIsEnabled: Boolean,
        val forcedGiftBoxTemplateId: String,
        val purchaseRequirements: List<JsonElement>,
        val giftRecordIds: List<JsonElement>
    )

    enum class ECatalogRequirementType {
        RequireFulfillment, DenyOnFulfillment, RequireItemOwnership, DenyOnItemOwnership
    }

    enum class ECatalogOfferType {
        StaticPrice, DynamicBundle
    }

    enum class ECatalogSaleType {
        NotOnSale, UndecoratedNewPrice, AmountOff, PercentOff, PercentOn, Strikethrough, MAX
    }
}


data class CalendarTimelineResponse(
    val channels: Map<String, States>,
    val eventsTimeOffsetHrs: Float,
    val cacheIntervalMins: Float,
    val currentTime: Date
) {
    data class States(
        val states: List<State>,
        val cacheExpire: Date
    )

    data class State(
        val validFrom: Date,
        val activeEvents: List<ActiveEvent>,
        val state: JsonObject
    )

    data class ClientEventState(
        val activeStorefronts: List<String>,
        val eventNamedWeights: JsonObject,
        val activeEvents: List<ActiveEvent2>,
        val seasonNumber: Int,
        val seasonTemplateId: String,
        val matchXpBonusPoints: Int,
        val seasonBegin: Date,
        val seasonEnd: Date,
        val seasonDisplayedEnd: Date,
        val weeklyStoreEnd: Date,
        val stwEventStoreEnd: Date,
        val stwWeeklyStoreEnd: Date,
        val dailyStoreEnd: Date
    )

    data class ActiveEvent2(
        val instanceId: String,
        val devName: String,
        val eventName: String,
        val eventStart: String,
        val eventEnd: String,
        val eventType: String
    )

    data class ActiveEvent(
        val eventType: String,
        val activeUntil: Date,
        val activeSince: Date
    )
}

data class FortItemStack(
    val templateId: String,
    val quantity: Int
)

data class WorldInfoResponse(
    val theaters: List<TheaterData>,
    val missions: List<MissionData>,
    val missionAlerts: List<MissionAlertData>
) {


    //theaters
    data class TheaterData(
        val displayName: String,
        val uniqueId: String,
        val theaterSlot: Int,
        val bIsTestTheater: Boolean,
        val bHideLikeTestTheater: Boolean,
        val requiredEventFlag: String,
        val missionRewardNamedWeightsRowName: String,
        val description: String,
        val runtimeInfo: JsonObject,
        val tiles: List<JsonObject>,
        val regions: List<JsonObject>
    )
    //end theaters

    // missions
    class MissionData(
        val availableMissions: List<Mission>,
        val nextRefresh: Date,
        theaterId: String
    ) : WithTheater(theaterId)

    data class Mission(
        val missionGuid: String,
        val missionRewards: TierGroup,
        val bonusMissionRewards: TierGroup,
        val missionGenerator: String,
        val missionDifficultyInfo: MissionDifficultyInfo,
        val tileIndex: Int,
        val availableUntil: Date
    )

    data class MissionDifficultyInfo(
        val dataTable: String,
        val rowName: String
    )
    //end missions

    //missionAlerts
    class MissionAlertData(
        val availableMissionAlerts: List<MissionAlert>,
        val nextRefresh: Date,
        theaterId: String
    ) : WithTheater(theaterId)

    data class MissionAlert(
        val name: String,
        val categoryName: String,
        val spreadDataName: String,
        val missionAlertGuid: String,
        val tileIndex: Int,
        val availableUntil: Date,
        val totalSpreadRefreshes: Int,
        val missionAlertRewards: TierGroup,
        val missionAlertModifiers: TierGroup
    )
    //end missionAlerts

    data class TierGroup(
        val tierGroupName: String,
        val items: List<WorldInfoItemStack>
    )

    data class WorldInfoItemStack(
        val itemType: String,
        val quantity: Int,
        @field:Transient
        private var stack: FortItemStack? = null
    ) {
        fun asItemStack(): FortItemStack {
            if (stack == null)
                stack = FortItemStack(itemType, quantity)
            return stack!!
        }
    }

    open class WithTheater(
        val theaterId: String
    ) {
        fun findTheater(data: WorldInfoResponse): TheaterData? {
            for (entry in data.theaters) {
                if (theaterId == entry.uniqueId) {
                    return entry
                }
            }
            return null
        }
    }
}

data class AccountPrivacyResponse(
    val accountId: String,
    val optOutOfPublicLeaderboards: Boolean
)

data class QueryMultipleUserStats(
    val owners: List<String>,
    val stats: List<String>
)

data class StatsV2Response(
    val accountId: String,
    val startTime: Long,
    val endTime: Long,
    val stats: Map<String, Int>
)

data class Receipt(
    val appStore: String,
    val appStoreId: String,
    val receiptId: String,
    val receiptInfo: String
)


data class LinksQueryResponse(
    val results: List<LinkEntry>,
    val hasMore: Boolean
)

data class LinkEntry(
    val sortDate: Date,
    val linkData: LinkData
) {
    data class LinkData(
        val mnemonic: String,
        val linkType: String,
        val active: Boolean,
        val version: Int,
        val accountId: String,
        val creatorName: String,
        val metadata: LinkMetadata
    )

    data class LinkMetadata(
        val alt_title: Map<String, String>,
        val alt_tagline: Map<String, String>,
        val alt_introduction: Map<String, String>,
        val image_url: String,
        val tagline: String,
        val islandType: String,
        val title: String,
        val locale: String,
        val supportCode: String,
        val matchmaking: MatchmakingData,
        val introduction: String
    )

    data class MatchmakingData(
        val targetNumberOfPlayers: Int,
        val maximumNumberOfPlayers: Int,
        val bAllowJoinInProgress: Boolean,
        val minimumNumberOfPlayers: Int
    )
}