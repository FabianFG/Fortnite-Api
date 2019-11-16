package me.fungames.fortnite.api.model.command

import me.fungames.fortnite.api.model.ECustomizationSlot
import me.fungames.fortnite.api.model.Variant
import me.fungames.fortnite.api.model.ECurrencyType
import me.fungames.fortnite.api.model.EMtxPlatform


//Generic
/**
 * com.epicgames.fortnite.core.game.commands.quests.FortRerollDailyQuest
 *
 *
 * Profile ID: campaign, athena
 */
data class FortRerollDailyQuest(
    /**
     * Daily Quest item ID to replace.
     */
    var questId: String
)

/**
 * com.epicgames.fortnite.core.game.commands.items.MarkItemSeen
 *
 *
 * Profile ID: anything with items
 */
data class MarkItemSeen(
    /**
     * Item ID(s) to be marked as seen.
     */
    var itemIds: List<String>
)

data class RemoveGiftBox(
    var giftBoxItemId: String
)

/**
 * com.epicgames.fortnite.core.game.commands.quests.MarkNewQuestNotificationSent
 *
 *
 * Profile ID: campaign, athena
 */
data class MarkNewQuestNotificationSent(
    var itemIds: List<String>
)

/**
 * com.epicgames.fortnite.core.game.commands.mtx.SetMtxPlatform
 *
 *
 * Profile ID: common_core
 */
data class SetMtxPlatform(
    /**
     * New V-Bucks transaction platform, to decide which V-Bucks platform to use when doing [PurchaseCatalogEntry] or [GiftCatalogEntry].
     */
    var newPlatform: EMtxPlatform
)

/**
 * Profile ID: common_core
 */
data class SetReceiveGiftsEnabled(
    var bReceiveGifts: Boolean
)

//BR

/**
 * com.epicgames.fortnite.core.game.commands.athena.SetPartyAssistQuest
 *
 *
 * Profile ID: athena
 */
data class SetPartyAssistQuest(
    /**
     * Quest item ID to pin as party assist. Leave this unset or empty string to clear it.
     */
    var questToPinAsPartyAssist: String
)

/**
 * com.epicgames.fortnite.core.game.commands.items.SetItemFavoriteStatusBatch
 *
 *
 * Profile ID: anything with items
 */
data class SetItemFavoriteStatusBatch(
    /**
     * Item ID(s) to be mapped to [.itemFavStatus].
     */
    var itemIds: List<String>,
    /**
     * Boolean array of items' favorite status, in the order of the items defined in [.itemIds].
     */
    var itemFavStatus: List<Boolean>
)


/**
 * com.epicgames.fortnite.core.game.commands.items.SetItemFavoriteStatus
 *
 *
 * Profile ID: anything with items except common_core
 */
data class SetItemFavoriteStatus(
    /**
     * Item ID(s) to modify the favorite status.
     */
    var targetItemId: String,
    /**
     * The favorite status to modify to.
     */
    var bFavorite: Boolean
)


/**
 * com.epicgames.fortnite.core.game.commands.athena.SetBattleRoyaleBanner
 *
 *
 * Profile ID: athena
 */
data class SetBattleRoyaleBanner(
    var homebaseBannerIconId: String,
    var homebaseBannerColorId: String
)

/**
 * com.epicgames.fortnite.core.game.commands.SetAffiliateName
 *
 *
 * Profile ID: common_core
 */
data class SetAffiliateName(
    /**
     * New Support-A-Creator code to set. Leave this unset or empty string to clear it.
     */
    var affiliateName: String
)

/**
 * com.epicgames.modules.gamesubcatalog.core.commands.RefundMtxPurchase
 *
 *
 * Profile ID: common_core
 */
data class RefundMtxPurchase(
    /**
     * Purchase ID to refund.
     */
    var purchaseId: String,
    /**
     * Set this to <tt>true</tt> for "Cancel Purchase", so no tickets will be consumed.
     */
    var quickReturn: Boolean
)

/**
 * Profile ID: athena
 */
data class ApplyVote(
    var offerId: String
)

/**
 * Profile ID: athena
 */
data class BulkEquipBattleRoyaleCustomization(
    /**
     * Define multiple EquipBattleRoyaleCustomization payloads here.
     */
    var loadoutData: List<EquipBattleRoyaleCustomization>
)

/**
 * com.epicgames.fortnite.core.game.commands.athena.EquipBattleRoyaleCustomization
 *
 *
 * Profile ID: athena
 */
data class EquipBattleRoyaleCustomization(
    /**
     * Slot to modify.
     */
    var slotName: ECustomizationSlot,
    /**
     * Item ID to be set into the slot.
     */
    var itemToSlot: String,
    /**
     * Index of the item in the slot. Starts at 0. Used for [ECustomizationSlot.Dance] and [ECustomizationSlot.ItemWrap]. You can use -1 to apply the Emote or Wrap to all slots.
     */
    var indexWithinSlot: Int,
    /**
     * Used to update the style of the item. The [Variant.owned] field **MUST** be an empty array. Leave this as an empty array if you wish not to update the item's style.
     */
    var variantUpdates: List<Variant>
)

/**
 * Profile ID: athena
 */
data class ChallengeBundleLevelUp(
    var bundleIdToLevel: String
)

/**
 * Profile ID: common_core
 */
class GiftCatalogEntry(
    /**
     * Account ID(s) of the recipient(s). (up to 4)
     */
    var receiverAccountIds: Array<String>,
    /**
     * Template ID of the GiftBox item to be shown on the recipient(s). Use this to change the appearance of the gift and item received screen.
     *
     *
     * Example: "GiftBox:gb_giftwrap1", "GiftBox:gb_giftwrap2", "GiftBox:gb_giftwrap3", "GiftBox:gb_giftwrap4"
     */
    var giftWrapTemplateId: String,
    /**
     * Personal message. Max length is 100.
     */
    var personalMessage: String,
    offerId: String,
    purchaseQuantity: Int,
    currency: ECurrencyType,
    currencySubType: String,
    expectedTotalPrice: Int,
    gameContext: String = ""
) : PurchaseCatalogEntry(offerId, purchaseQuantity, currency, currencySubType, expectedTotalPrice, gameContext)

/**
 * com.epicgames.fortnite.core.game.commands.mtx.FortPurchaseCatalogEntry
 *
 *
 * Profile ID: common_core
 */
open class PurchaseCatalogEntry(
    var offerId: String,
    var purchaseQuantity: Int,
    var currency: ECurrencyType,
    var currencySubType: String,
    var expectedTotalPrice: Int,
    var gameContext: String = ""
)


//STW

/**
 * Profile ID: campaign
 */
data class AbandonExpedition(
    var expeditionId: String
)

/**
 * Profile ID: campaign
 */
data class ActivateConsumable(
    /**
     * Item ID of a consumable to apply to [.targetAccountId].
     */
    var targetItemId: String,
    /**
     * Account ID of the player to have the consumable item applied to.
     */
    var targetAccountId: String
)

/**
 * Profile ID: campaign
 */
data class AssignGadgetToLoadout(
    var gadgetId: String,
    var loadoutId: String,
    var slotIndex: Int
)

/**
 * Profile ID: campaign
 */
data class AssignHeroToLoadout(
    var heroId: String,
    var loadoutId: String,
    var slotName: String
)

/**
 * Profile ID: campaign
 */
data class AssignTeamPerkToLoadout(
    var teamPerkId: String,
    var loadoutId: String
)

/**
 * Profile ID: campaign
 */
data class AssignWorkerToSquadBatch(
    var characterIds: List<String>,
    var squadIds: List<String>,
    var slotIndices: List<Int>
)

/**
 * com.epicgames.fortnite.core.game.commands.homebase.ClaimCollectedResources
 *
 *
 * Profile ID: campaign
 */
data class ClaimCollectedResources(
    /**
     * Item ID(s) of CollectedResource item(s) to collect.
     */
    var collectorsToClaim: List<String>
)

/**
 * TODO: documentation
 * <p>
 * Profile ID: campaign
 */
class ClaimMissionAlertRewards

/**
 * Claim a quest's rewards, which the <tt>quest_state</tt> attribute value of that quest is <tt>Completed</tt>. Adds the rewards to the inventory and changes the <tt>quest_state</tt> attribute value to <tt>Claimed</tt>.
 *
 *
 * Profile ID: campaign
 */
data class ClaimQuestReward(
    /**
     * Quest item ID to claim its rewards.
     */
    var questId: String,
    /**
     * Index of the reward item for choice-based reward items. Starts at 0. Set this to -1 for non-choice based rewards.
     */
    var selectedRewardIndex: Int
)

/**
 * Updates the daily quests/challenges.
 */
class ClientQuestLogin

/**
 * Profile ID: campaign
 */
data class ConvertItem(
    /**
     * The item ID to evolve.
     */
    var targetItemId: String,
    /**
     * The index of the evolution item choices to evolve this item to.
     */
    var conversionIndex: Int
)

/**
 * Profile ID: theater0
 */
data class CraftWorldItem(
    /**
     * Item ID of the Schematic used to craft the item.
     */
    var targetSchematicItemId: String,
    /**
     * Quantity of item(s) to craft.
     */
    var numTimesToCraft: Int,
    /**
     * Tier of the item(s) to craft. For tiered items, use the roman number in lowercase (e.g. "i", "ii", "iii", "iv", "v", "vi"). Use "no_tier" for non-tiered items.
     */
    var targetSchematicTier: String
)

/**
 * Profile ID: campaign
 */
data class OpenCardPack(
    /**
     * The CardPack item ID to open.
     */
    var cardPackItemId: String,
    /**
     * Used to choose which item to be added to the inventory in a Choice Pack.
     */
    var selectionIdx: Int
)

/**
 * Profile ID: campaign
 */
data class OpenCardPackBatch(
    /**
     * The CardPack item ID(s) to open.
     */
    var cardPackItemIds: List<String>
)

/**
 * Profile ID: campaign
 */
data class PurchaseOrUpgradeHomebaseNode(
    var nodeId: String
)

/**
 * Profile ID: campaign
 */
data class PurchaseResearchStatUpgrade(
    var statId: String
)

/**
 * Loads the profile data. No operation will be performed.
 */
class QueryProfile

/**
 * Profile ID: campaign
 */
data class RecycleItemBatch(
    /**
     * Item ID(s) to recycle.
     */
    var targetItemIds: List<String>
)

/**
 * Updates the items related to expeditions.
 * <p>
 * Profile ID: campaign
 */
class RefreshExpeditions

/**
 * Replace item perk.
 *
 *
 * Profile ID: campaign
 */
data class RespecAlteration(
    /**
     * Item ID of the item that you want one of its perks replaced.
     */
    var targetItemId: String,
    /**
     * Index of the perk within the <tt>alterations</tt> field inside the <tt>attributes</tt> object. Starts at 0.
     */
    var alterationSlot: Int,
    /**
     * Template ID of the perk to replace to.
     */
    var alterationId: String
)

/**
 * Profile ID: campaign
 */
data class SetActiveHeroLoadout(
    var selectedLoadout: String
)

/**
 * com.epicgames.fortnite.core.game.commands.homebase.SetHomebaseBanner
 *
 *
 * Profile ID: common_public
 */
data class SetHomebaseBanner(
    var homebaseBannerIconId: String,
    var homebaseBannerColorId: String
)

/**
 * Let's get crazy, this works! You can actually change your homebase name!
 *
 *
 * com.epicgames.fortnite.core.game.commands.homebase.SetHomebaseName
 *
 *
 * Profile ID: common_public
 */
data class SetHomebaseName(
    /**
     * New Homebase Name to set. Can be set on non-STW owning accounts. Once set, can't be unset or cleared.
     */
    var homebaseName: String
)

/**
 * Profile ID: campaign
 */
data class SetPinnedQuests(
    /**
     * Quest item ID(s) to be set as pinned.
     *
     *
     * Gather and modify data from CAMPAIGN_PROFILE_ATTRIBUTES.client_settings.
     */
    var pinnedQuestIds: List<String>
)

/**
 * Profile ID: campaign
 */
data class SlotItemInCollectionBook(
    /**
     * Item ID to add to collection book.
     */
    var itemId: String
)

/**
 * Profile ID: campaign
 */
data class StartExpedition(
    /**
     * ex: e9bf2684-7dd8-4db1-ac89-91cde05e1a66
     */
    var expeditionId: String,
    /**
     * ex: Squad_Expedition_ExpeditionSquadThree
     */
    var squadId: String,
    /**
     * ex: ["be1eaeba-34d5-4aea-ada1-a36e8fcb9e21", "fe181eaf-c0ce-4cab-82c6-13c469ea5bf9", "95473911-b50b-4314-92f1-f64eccb60481", "818e851b-b4f0-48eb-82da-6b410722ec57"]
     */
    var itemIds: List<String>,
    /**
     * ex: [ 0, 1, 2, 3 ]
     */
    var slotIndices: List<Int>
)

/**
 * Profile ID: theater0
 */
data class StorageTransfer(
    /**
     * The transfer operations to perform.
     */
    var transferOperations: List<TransferOperation>
) {

    data class TransferOperation(
        /**
         * Item ID to transfer.
         */
        var itemId: String,
        /**
         * Quantity of items in stack to transfer.
         */
        var quantity: Int,
        /**
         * Self explanatory.
         */
        var toStorage: Boolean,
        /**
         * Leave this as empty string.
         * TODO find out what is this field.
         */
        var newItemIdHint: String
    )
}

/**
 * Transform item.
 *
 *
 * Profile ID: campaign
 */
data class TransmogItem(
    /**
     * ConversionControl item template ID to use as the transform key.
     */
    var transmogKeyTemplateId: String,
    /**
     * Item ID(s) used in the transform sequence.
     */
    var sacrificeItemIds: List<String>
)

/**
 * Upgrade item perk.
 *
 *
 * Profile ID: campaign
 */
data class UpgradeAlteration(
    /**
     * Item ID of the item that you want one of its perks upgraded.
     */
    var targetItemId: String,
    /**
     * Index of the perk within the <tt>alterations</tt> field inside the <tt>attributes</tt> object. Starts at 0.
     */
    var alterationSlot: Int
)

/**
 * Profile ID: campaign
 */
data class UpgradeItem(
    /**
     * The item ID to upgrade.
     */
    var targetItemId: String
)



