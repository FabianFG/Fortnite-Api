package me.fungames.fortnite.api.model

data class ManifestInfoResponse (
    val appName : String,
    val labelName : String,
    val buildVersion : String,
    val catalogItemId : String,
    val expires : String,
    val items : Items,
    val assetId : String
)

data class Items(
    val MANIFEST : ManifestInfo,
    val CHUNKS : ManifestInfo
)

data class ManifestInfo(
    val signature : String,
    val distribution : String,
    val path : String,
    val hash : String,
    val additionalDistributions : List<String>
)