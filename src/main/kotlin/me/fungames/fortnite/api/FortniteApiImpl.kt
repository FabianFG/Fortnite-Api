package me.fungames.fortnite.api

import me.fungames.fortnite.api.exceptions.EpicErrorException
import me.fungames.fortnite.api.model.LoginResponse
import java.io.IOException
import me.fungames.fortnite.api.model.EpicError
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import com.google.gson.GsonBuilder
import me.fungames.fortnite.api.events.Event
import me.fungames.fortnite.api.model.notification.ProfileNotification
import me.fungames.fortnite.api.network.DefaultInterceptor
import me.fungames.fortnite.api.network.services.*
import okhttp3.Cache
import okhttp3.JavaNetCookieJar
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.*
import java.util.concurrent.TimeUnit


class FortniteApiImpl internal constructor(): FortniteApi {

    var clientLauncherToken: String = ClientToken.FN_IOS_GAME_CLIENT.token

    override var isLoggedIn = false
        private set

    override var language: String = "en"

    private val gson = GsonBuilder()
        .registerTypeAdapter(ProfileNotification::class.java, ProfileNotification.Serializer())
        .create()!!
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .cookieJar(JavaNetCookieJar(CookieManager(null, CookiePolicy.ACCEPT_ALL)))
        .cache(Cache(Utils.cacheDirFile, 4 * 1024 * 1024))
        .addInterceptor(DefaultInterceptor(this))
        .build()

    override val accountPublicService: AccountPublicService
    override val affiliatePublicService : AffiliatePublicService
        get() {
            verifyToken()
            return field
        }
    override val catalogPublicService : CatalogPublicService
        get() {
            verifyToken()
            return field
        }
    override val epicGamesService : EpicGamesService
    override val eventsPublicService : EventsPublicService
        get() {
            verifyToken()
            return field
        }
    override val fortniteContentWebsiteService : FortniteContentWebsiteService
        get() {
            verifyToken()
            return field
        }
    override val fortnitePublicService : FortnitePublicService
        get() {
            verifyToken()
            return field
        }
    override val friendsPublicService : FriendsPublicService
        get() {
            verifyToken()
            return field
        }
    override val launcherPublicService: LauncherPublicService
        get() {
            verifyToken()
            return field
        }
    override val partyService : PartyService
        get() {
            verifyToken()
            return field
        }
    override val personaPublicService : PersonaPublicService
        get() {
            verifyToken()
            return field
        }



    override val accountTokenType: String
        get() {
            checkNotNull(epicAccountTokenType) { "Api is not logged in" }
            return epicAccountTokenType!!
        }

    override val accountToken: String
        get() {
            checkNotNull(epicAccountAccessToken) { "Api is not logged in" }
            return epicAccountAccessToken!!
        }

    override val accountExpiresAtMillis: Long
        get() {
            checkNotNull(epicAccountExpiresAtMillis) { "Api is not logged in" }
            return epicAccountExpiresAtMillis!!
        }

    override val accountId: String
        get() {
            checkNotNull(epicAccountId) { "Api is not logged in" }
            return epicAccountId!!
        }

    internal var email : String? = null
    internal var password : String? = null

    internal var epicAccountId: String? = null
    internal var deviceId : String? = null
    internal var secret : String? = null

    private var epicAccountTokenType : String? = null
    private var accountExpiresAt: Date? = null
    private var epicAccountExpiresAtMillis: Long? = null
    private var accountRefreshToken: String? = null
    private var epicAccountAccessToken: String? = null

    init {
        val retrofitBuilder = Retrofit.Builder().client(httpClient).addConverterFactory(GsonConverterFactory.create(gson))
        accountPublicService =
        retrofitBuilder.baseUrl(AccountPublicService.BASE_URL).build().create(
            AccountPublicService::class.java)
        affiliatePublicService =
            retrofitBuilder.baseUrl(AffiliatePublicService.BASE_URL).build().create(
                AffiliatePublicService::class.java)
        catalogPublicService =
            retrofitBuilder.baseUrl(CatalogPublicService.BASE_URL).build().create(
                CatalogPublicService::class.java)
        epicGamesService =
            retrofitBuilder.baseUrl(EpicGamesService.BASE_URL).build().create(
                EpicGamesService::class.java)
        eventsPublicService =
            retrofitBuilder.baseUrl(EventsPublicService.BASE_URL).build().create(
                EventsPublicService::class.java)
        fortniteContentWebsiteService =
            retrofitBuilder.baseUrl(FortniteContentWebsiteService.BASE_URL).build().create(
                FortniteContentWebsiteService::class.java)
        fortnitePublicService =
            retrofitBuilder.baseUrl(FortnitePublicService.BASE_URL).build().create(
                FortnitePublicService::class.java)
        friendsPublicService =
            retrofitBuilder.baseUrl(FriendsPublicService.BASE_URL).build().create(
                FriendsPublicService::class.java)
        launcherPublicService =
            retrofitBuilder.baseUrl(LauncherPublicService.BASE_URL).build().create(
                LauncherPublicService::class.java)
        partyService =
            retrofitBuilder.baseUrl(PartyService.BASE_URL).build().create(
                PartyService::class.java)
        personaPublicService =
            retrofitBuilder.baseUrl(PersonaPublicService.BASE_URL).build().create(
                PersonaPublicService::class.java)
    }

    override fun loginDeviceAuth(token: ClientToken) {
        if (epicAccountId == null || deviceId == null || secret == null)
            throw EpicErrorException("To use device auth, account id, device id and secret must be defined")
        val deviceAuthLogin = this.accountPublicService.grantToken("basic ${token.token}", "device_auth", mapOf("account_id" to epicAccountId!!, "device_id" to deviceId!!, "secret" to secret!!, "token_type" to "eg1"), null).execute()
        if (!deviceAuthLogin.isSuccessful)
            throw EpicErrorException(EpicError.parse(deviceAuthLogin))
        loginSucceeded(deviceAuthLogin.body()!!)
        val exchange = this.accountPublicService.exchangeToken().execute()
        if (!exchange.isSuccessful)
            throw EpicErrorException(EpicError.parse(deviceAuthLogin))
        val exchangeCode = exchange.body()!!.code
        val loginRequest = this.accountPublicService.grantToken("basic $clientLauncherToken", "exchange_code", mapOf("exchange_code" to exchangeCode), null)
        try {
            val response = loginRequest.execute()
            if (response.isSuccessful)
                loginSucceeded(response.body()!!)
            else
                throw EpicErrorException(EpicError.parse(response))

        } catch (e: IOException) {
            throw EpicErrorException(e)
        }
    }

    override fun loginClientCredentials() {
        val loginRequest =
            this.accountPublicService.grantToken("basic $clientLauncherToken", "client_credentials", emptyMap(), false)
        try {
            val response = loginRequest.execute()
            if (response.isSuccessful)
                loginSucceeded(response.body()!!)
            else
                throw EpicErrorException(EpicError.parse(response))

        } catch (e: IOException) {
            throw EpicErrorException(e)
        }
    }

    override fun login(rememberMe: Boolean) = login(rememberMe, 0)

    fun login(rememberMe: Boolean, retryCount : Int, overrideXsrfToken : String? = null) {
        val loginEmail = email
        val loginPassword = password
        if (loginEmail == null || loginPassword == null) {
            return if (epicAccountId != null && deviceId != null && secret != null)
                loginDeviceAuth()
            else
                loginClientCredentials()
        }
        val reputation = this.epicGamesService.captcha().execute()
        if (!reputation.isSuccessful /*|| reputation.body()?.get("verdict")?.asBoolean == false*/) {
            if (retryCount >= 10)
                throw EpicErrorException("Failed to login to Epic Api: Captcha did not return allow")
            else return login(rememberMe, retryCount + 1)
        }

        var xsrfToken = overrideXsrfToken
        if (xsrfToken == null) {
            val csrf = this.epicGamesService.csrfToken().execute()
            if (!csrf.isSuccessful)
                throw EpicErrorException(EpicError.parse(csrf))
            xsrfToken = csrf.headers().toMultimap()["Set-Cookie"]?.first { it.startsWith("XSRF-TOKEN=") }
                ?.substringAfter("XSRF-TOKEN=")?.substringBefore(';')
                ?: throw EpicErrorException("Failed to obtain xsrf token")
        }
        val login = this.epicGamesService.login(mapOf("email" to loginEmail, "password" to loginPassword, "rememberMe" to rememberMe.toString()), xsrfToken).execute()
        if (login.code() == 409) {
            if (retryCount >= 10)
                throw EpicErrorException("Failed to login to Epic Api: Conflict 409")
            else return login(rememberMe, retryCount + 1, xsrfToken)
        }
        if (!login.isSuccessful) {
            if (retryCount >= 10)
                throw EpicErrorException("Failed to login to Epic Api: ${login.code()} ${EpicError.parse(login).errorMessage}")
            else return login(rememberMe, retryCount + 1)
        }
        val exchange = this.epicGamesService.exchange().execute()
        if (!exchange.isSuccessful)
            throw EpicErrorException(EpicError.parse(exchange))
        val exchangeCode = exchange.body()!!.code
        val auth = this.accountPublicService.grantToken("basic $clientLauncherToken", "exchange_code", mapOf("exchange_code" to exchangeCode, "token_type" to "eg1"), false).execute()
        if (!auth.isSuccessful)
            throw EpicErrorException(EpicError.parse(auth))
        loginSucceeded(auth.body()!!)
    }

    private fun verifyToken() {
        require(isLoggedIn) { "Api is not logged in" }
        if (System.currentTimeMillis() >= this.epicAccountExpiresAtMillis!!) {
            if (this.accountRefreshToken == null) {
                login()
                return
            }
            val refresh = this.accountPublicService.grantToken("basic $clientLauncherToken", "refresh_token", mapOf("refresh_token" to this.accountRefreshToken!!), null).execute()
            if (!refresh.isSuccessful) {
                System.err.println("Failed to use refresh token")
                System.err.println(EpicError.parse(refresh).errorMessage)
                System.err.println("Attempting to relogin")
                login()
                return
            }
            loginSucceeded(refresh.body()!!)
        }
    }

    internal fun loginSucceeded(response: LoginResponse) {
        this.epicAccountAccessToken = response.access_token
        this.accountExpiresAt = response.expires_at
        this.epicAccountExpiresAtMillis = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(response.expires_in.toLong(), TimeUnit.SECONDS)
        this.epicAccountId = response.account_id
        this.accountRefreshToken = response.refresh_token
        this.epicAccountTokenType = response.token_type
        this.isLoggedIn = true
    }

    @Throws(EpicErrorException::class)
    override fun logout() {
    }

    override fun fireEvent(event: Event) {
        println("Received " + event::class.java.simpleName)
        verifyToken()
    }

    private var xmpp: XMPPService? = null

    override val xmppService: XMPPService
        get() {
            if (xmpp == null) xmpp = XMPPService(this)
            return xmpp!!
        }
}