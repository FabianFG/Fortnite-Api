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

data class ClientDetails(
    val abis : List<String>,
    val apiLevel : Int,
    val coreCount : Int,
    val hardwareName : String,
    val hasLibHoudini : Boolean,
    val machineId : String,
    val manufacturer : String,
    val memoryMiB : Int,
    val model : String,
    val platform : String,
    val preInstallInfo : String,
    val renderingDevice : String,
    val renderingDriver : String,
    val supportsArmNEON : Boolean,
    val supportsFpRenderTargets : Boolean,
    val textureCompressionFormats : List<String>,
    val version : String
)

data class BuildResponse(
    val buildStatuses : List<BuildStatus>?,
    val elements : List<BuildInfo>
)

data class BuildStatus(
    val app : String,
    val status : String,
    val version : String?
)

data class BuildInfo(
    val appName : String,
    val buildVersion : String,
    val hash : String,
    val labelName: String,
    val manifests : List<ManifestDownloadInfo>,
    val metadata: Map<String, String>
) {
    data class ManifestDownloadInfo(
        val headers : List<Header>?,
        val queryParams : List<QueryParam>?,
        val uri : String
    )

    data class Header(
        val name : String,
        val value : String
    )

    data class QueryParam(
        val name : String,
        val value : String
    )
}

data class PackageInstallerDetails(
    val installerPackageName : String,
    val machineId : String
)