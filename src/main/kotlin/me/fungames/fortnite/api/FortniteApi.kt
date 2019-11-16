package me.fungames.fortnite.api

import me.fungames.fortnite.api.events.Event
import me.fungames.fortnite.api.exceptions.EpicErrorException
import me.fungames.fortnite.api.network.services.*


interface FortniteApi {
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
    val partyService : PartyService
    val personaPublicService : PersonaPublicService


    val language: String
    val accountTokenType: String

    val accountToken: String
}