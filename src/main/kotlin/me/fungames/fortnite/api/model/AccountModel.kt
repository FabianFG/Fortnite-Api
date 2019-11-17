package me.fungames.fortnite.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class DeviceAuth(
    var deviceId: String,
    var accountId: String,
    /**
     * only in device registration request
     */
    var secret: String,
    var userAgent: String,
    var deviceInfo: DeviceInfo,
    var created: LocationIpDate,
    var lastAccess: LocationIpDate
) {

    data class DeviceInfo(var type: String, var model: String, var os: String)

    data class LocationIpDate(
        var location: String,
        var ipAddress: String,
        var dateTime: Date
    )
}

data class ExchangeResponse(
    var code : String,
    var creatingClientId: String,
    var expiresInSeconds: Int
)

data class ExternalAuth(
    var accountId: String,
    var type: String,
    var authIds: List<AuthId>,
    var externalAuthId: String,
    var externalAuthSecondaryId: String,
    var dateAdded: Date,
    var externalDisplayName: String,
    var externalAuthIdType: String,
    var lastLogin: Date
) {
    data class AuthId(var id: String, var type: String)
}

open class GameProfile(
    id : String, displayName: String, @field:SerializedName("externalAuths") val externalAuths: Map<String, ExternalAuth>
) {
    @field:SerializedName("id") var id: String = id
        set(value) {
            require(value.length == 32) { "length != 32" }
            field = value
        }
    @field:SerializedName("displayName") var displayName: String = displayName
        set(value) {
            require(value.length >= 3) { "length < 3" }
            field = value
        }
}
open class BaseOauthResponse(
    var account_id: String,
    var client_id: String,
    var client_service: String,
    /**
     * Nonexistent if X-Epic-Device-Id header isn't provided
     */
    var device_id: String,
    var expires_at: Date,
    var expires_in: Int,
    var in_app_id: String,
    var internal_client: Boolean?,
    var lastPasswordValidation: Date,
    var perms: Array<Perm>,
    var token_type: String
)

data class Perm(
    var resource: String, // flags
    var action: Int
)

class LoginResponse(
    account_id: String,
    client_id: String,
    client_service: String,
    device_id: String,
    expires_at: Date,
    expires_in: Int,
    in_app_id: String,
    internal_client: Boolean?,
    lastPasswordValidation: Date,
    perms: Array<Perm>,
    token_type: String,
    var access_token: String,
    var app: String,
    var refresh_expires: String,
    var refresh_expires_at: Date,
    var refresh_token: String
) : BaseOauthResponse(
    account_id,
    client_id,
    client_service,
    device_id,
    expires_at,
    expires_in,
    in_app_id,
    internal_client,
    lastPasswordValidation,
    perms,
    token_type
)

data class QueryExternalIdMappingsByIdPayload(var ids: List<String>, var authType: String)

class VerifyResponse(
    account_id: String, client_id: String, client_service: String, device_id: String, expires_at: Date,
    expires_in: Int, in_app_id: String, internal_client: Boolean?, lastPasswordValidation: Date, perms: Array<Perm>,
    token_type: String, var app: String, var auth_method: String, var session_id: String, var token: String
) : BaseOauthResponse(
    account_id,
    client_id,
    client_service,
    device_id,
    expires_at,
    expires_in,
    in_app_id,
    internal_client,
    lastPasswordValidation,
    perms,
    token_type
)

class XGameProfile(
    id: String,
    displayName: String,
    externalAuths: Map<String, ExternalAuth>,
    var name: String,
    var email: String,
    var failedLoginAttempts: Int?,
    var lastFailedLogin: String,
    var lastLogin: String,
    var numberOfDisplayNameChanges: Int?,
    var ageGroup: String,
    var headless: Boolean?,
    var country: String,
    var lastName: String,
    var phoneNumber: String,
    var preferredLanguage: String,
    var lastDisplayNameChange: Date,
    var canUpdateDisplayName: Boolean?,
    var tfaEnabled: Boolean?
) : GameProfile(id, displayName, externalAuths)