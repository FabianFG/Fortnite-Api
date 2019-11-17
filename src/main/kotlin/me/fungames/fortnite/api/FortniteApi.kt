package me.fungames.fortnite.api

import me.fungames.fortnite.api.events.Event
import me.fungames.fortnite.api.exceptions.EpicErrorException
import me.fungames.fortnite.api.network.services.*


interface FortniteApi {

    class Builder {
        private var email: String? = null
        private var password: String? = null
        private var loginAsUser : Boolean = true
        private var clientToken: String? = null

        fun email(email: String): Builder {
            this.email = email
            return this
        }
        fun password(password: String): Builder {
            this.password = password
            return this
        }
        fun loginAsUser(loginAsUser : Boolean): Builder {
            this.loginAsUser = loginAsUser
            return this
        }
        fun clientToken(token: String): Builder {
            this.clientToken = token
            return this
        }

        fun build() = FortniteApiImpl().apply { if (clientToken != null) this.clientLauncherToken = clientToken!! }

        fun buildAndLogin(): FortniteApi {
            val api = build()
            if (loginAsUser) {
                check(email != null && password != null) { "Logging in as user requires email and password" }
                api.login(email!!, password!!)
            } else {
                api.loginClientCredentials()
            }
            return api
        }
    }

    val isLoggedIn: Boolean

    @Throws(EpicErrorException::class)
    fun loginClientCredentials()
    @Throws(EpicErrorException::class)
    fun login(email : String, password : String, rememberMe : Boolean = false)

    @Throws(EpicErrorException::class)
    fun logout()

    fun fireEvent(event: Event)

    //All the Services available
    val accountPublicService: AccountPublicService
    val affiliatePublicService : AffiliatePublicService
    val catalogPublicService : CatalogPublicService
    val epicGamesService : EpicGamesService
    val eventsPublicService : EventsPublicService
    val fortniteContentWebsiteService : FortniteContentWebsiteService
    val fortnitePublicService : FortnitePublicService
    val friendsPublicService : FriendsPublicService
    val launcherPublicService : LauncherPublicService
    val partyService : PartyService
    val personaPublicService : PersonaPublicService


    var language: String
    val accountTokenType: String
    val accountToken: String
    val accountExpiresAtMillis: Long
}