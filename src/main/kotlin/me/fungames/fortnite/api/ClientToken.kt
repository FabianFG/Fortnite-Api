package me.fungames.fortnite.api

import java.util.*

enum class ClientToken(val id: String, secret: String) {

    FN_PC_GAME_CLIENT("ec684b8c687f479fadea3cb2ad83f5c6", "e1f31c211f28413186262d37a13fc84d"),
    FN_IOS_GAME_CLIENT("3446cd72694c4a4485d81b77adbb2141", "9209d4a5e25a457fb9b07489d313b41a"),
    FN_AND_GAME_CLIENT("3f69e56c7649492c8cc29f1af08a8a12", "b51ee9cb12234f50a69efa67ef53812e"),
    FN_CN_GAME_CLIENT("efe3cbb938804c74b20e109d0efc1548", "6e31bdbae6a44f258474733db74f39ba"),
    KAIROS_PC("5b685653b9904c1d92495ee8859dcb00", "7Q2mcmneyuvPmoRYfwM7gfErA6iUjhXr"),
    KAIROS_IOS("61d2f70175e84a6bba80a5089e597e1c", "FbiZv3wbiKpvVKrAeMxiR6WhxZWVbrvA"),
    KAIROS_AND("0716a69cb8b2422fbb2a8b0879501471", "cGthdfG68tyE7M3ZHMu3sXUBwqhibKFp"),
    LAUNCHER_APP_CLIENT_2("34a02cf8f4414e29b15921876da36f9a", "daafbccc737745039dffe53d94fc76cf"),
    ANDROID_PORTAL("38dbfc3196024d5980386a37b7c792bb", "a6280b87-e45e-409b-9681-8f15eb7dbcf5"),
    UT_CLIENT("1252412dc7704a9690f6ea4611bc81ee", "2ca0c925b4674852bff92b26f8322434"),
    WEX_IOS_GAME_CLIENT("ec813099a59f48d4a338f1901c1609db", "72f6db62-0e3e-4439-97df-ee21f7b0ae94"),
    FN_VALKYRIE_GAME_CLIENT("3e13c5c57f594a578abe516eecb673fe", "530e316c337e409893c55ec44f22cd62"),
    WEX_PC_GAME_CLIENT("3cf78cd3b00b439a8755a878b160c7ad", "b383e0f4-f0cc-4d14-99e3-813c33fc1e9d"),
    DIESEL_DAUNTLESS("b070f20729f84693b5d621c904fc5bc2", "HG@XE&TGCxEJsgT#&_p2]=aRo#~>=>+c6PhR)zXP"),
    EOS_SDK_AUTH_TOOL("8f50327ba00d4ebeb81991ee04a42fc1", "0b0d21c7-c195-4c75-abb0-00ebc36b60f5"),
    EOS_SDK_AUTH_TOOL_GAMEDEV("dc1b76662b824a11a3de81b7aabc2169", "3ed26ae3-c7fc-4fea-949a-dc6b7cee7b25");

    val token: String = Base64.getEncoder().encodeToString("$id:$secret".toByteArray())

}