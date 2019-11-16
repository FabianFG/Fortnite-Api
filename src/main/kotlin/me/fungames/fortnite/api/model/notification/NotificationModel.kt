package me.fungames.fortnite.api.model.notification

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import me.fungames.fortnite.api.Utils
import me.fungames.fortnite.api.model.LootResult
import java.lang.reflect.Type


open class ProfileNotification(
    val type: String,
    val primary: Boolean
) {
    class Serializer : JsonDeserializer<ProfileNotification> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ProfileNotification {
            var type: Class<out ProfileNotification> = ProfileNotification::class.java
            when (json.asJsonObject.get("type").runCatching { this.asString }.getOrDefault("")) {
                "catalogPurchase" -> type = CatalogPurchaseNotification::class.java
                "collectedResourceResult" -> type = CollectedResourceResultNotification::class.java
                "conversionResult" -> type = ConversionResultNotification::class.java
                "daily_rewards" -> type = DailyRewardsNotification::class.java
            }
            return Utils.DEFAULT_GSON.fromJson(json, type)
        }
    }
}

class CatalogPurchaseNotification(
    val lootResult : LootResultRoot,
    type: String,
    primary: Boolean
) : ProfileNotification(type, primary) {
    data class LootResultRoot(
        val items: List<LootResult>
    )
}

class CollectedResourceResultNotification(
    val loot : LootResultRoot,
    type: String,
    primary: Boolean
) : ProfileNotification(type, primary) {
    data class LootResultRoot(
        val items: List<LootResult>
    )
}

class ConversionResultNotification(
    val itemsGranted : List<LootResult>,
    type: String,
    primary: Boolean
) : ProfileNotification(type, primary)

class DailyRewardsNotification(
    val daysLoggedIn: Int,
    val items: Array<LootResult>,
    type: String,
    primary: Boolean
) : ProfileNotification(type, primary)