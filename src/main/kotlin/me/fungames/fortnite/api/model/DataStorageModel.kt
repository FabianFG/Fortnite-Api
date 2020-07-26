package me.fungames.fortnite.api.model

data class DSFile(val readLink: String?,
                  val writeLink: String?,
                  val hash: String?,
                  val lastModified: String?,
                  val size: Int,
                  val fileLocked: Boolean)

data class DSQueryResponse(val files: Map<String, DSFile>,
                           val folderThrottled: Boolean,
                           val maxSizeFileBytes: Int,
                           val maxFolderSizeBytes: Int,
                           val expiresAt: String)