package me.fungames.fortnite.api.model

import java.util.*
import com.google.gson.JsonElement
import com.google.gson.JsonObject


class FortBasicDataResponse(
    var survivalmessage: JsonObject,
    var athenamessage: JsonObject,
    var subgameselectdata: JsonObject,
    var savetheworldnews: NewsRoot,
    var battleroyalenews: NewsRoot,
    var loginmessage: JsonObject,
    var playlistimages: PlaylistImages,
    var battlepassaboutmessages: NewsRoot,
    var playlistinformation: PlaylistInformation,
    var tournamentinformation: TournamentInformation,
    var emergencynotice: NewsRoot,
    var koreancafe: JsonObject,
    var creativeAds: CreativeAds,
    var playerSurvey: JsonObject,
    var creativeFeatures: CreativeAds,
    var lobby: LobbyInfo,
    var creativenews: NewsRoot,
    var _suggestedPrefetch: List<JsonElement>,
    _title: String,
    _activeDate: Date,
    lastModified: Date,
    _locale: String
) : FortBasicDataResponseBase(_title, _activeDate, lastModified, _locale) {

    data class CommonUISimpleMessageBase(
        var layout: String,
        var image: String,
        var hidden: Boolean,
        var messagetype: String,
        var _type: String,
        var adspace: String,
        var title: String,
        var body: String,
        var spotlight: Boolean
    )

    data class CommonUISimpleMessagePlatform(
        var hidden: Boolean,
        var _type: String,
        var message: CommonUISimpleMessageBase,
        var platform: String
    )

    class NewsRoot(
        var news: News,
        var header: String,
        var style: String,
        _title: String,
        _activeDate: Date,
        lastModified: Date,
        _locale: String
    ) : FortBasicDataResponseBase(_title, _activeDate, lastModified, _locale)

    data class News(
        var platform_messages: List<CommonUISimpleMessagePlatform>,
        var _type: String,
        var messages: List<CommonUISimpleMessageBase>
    )

    class PlaylistInformation(
        var frontend_matchmaking_header_style: String,
        var frontend_matchmaking_header_text: String,
        var playlist_info: PlaylistInformationList,
        _title: String,
        _activeDate: Date,
        lastModified: Date,
        _locale: String
    ) : FortBasicDataResponseBase(_title, _activeDate, lastModified, _locale)

    data class PlaylistInformationList(
        var playlists: List<FortPlaylistInfo>,
        var _type: String
    )

    data class FortPlaylistInfo(
        var image: String,
        var playlist_name: String,
        var _type: String
    )

    class TournamentInformation(
        var tournament_info: TournamentsInfo,
        _title: String,
        _activeDate: Date,
        lastModified: Date,
        _locale: String
    ) : FortBasicDataResponseBase(_title, _activeDate, lastModified, _locale)

    data class TournamentsInfo(
        var tournaments: List<TournamentDisplayInfo>,
        var _type: String
    )

    data class TournamentDisplayInfo(
        var title_color: String,
        var loading_screen_image: String,
        var background_text_color: String,
        var background_right_color: String,
        var poster_back_image: String,
        var _type: String,
        var pin_earned_text: String,
        var tournament_display_id: String,
        var highlight_color: String,
        var schedule_info: String,
        var primary_color: String,
        var flavor_description: String,
        var poster_front_image: String,
        var short_format_title: String,
        var title_line_2: String,
        var title_line_1: String,
        var shadow_color: String,
        var details_description: String,
        var background_left_color: String,
        var long_format_title: String,
        var poster_fade_color: String,
        var secondary_color: String,
        var playlist_tile_image: String,
        var base_color: String
    )

    class PlaylistImages(
        var playlistimages: PlaylistImageList,
        _title: String,
        _activeDate: Date,
        lastModified: Date,
        _locale: String
    ) : FortBasicDataResponseBase(_title, _activeDate, lastModified, _locale)

    data class PlaylistImageList(
        var images: List<PlaylistImageEntry>,
        var _type: String
    )

    data class PlaylistImageEntry(
        var image: String,
        var _type: String,
        var playlistname: String
    )

    class CreativeAds(
        var ad_info: CreativeAdInfo,
        _title: String,
        _activeDate: Date,
        lastModified: Date,
        _locale: String
    ) : FortBasicDataResponseBase(_title, _activeDate, lastModified, _locale)

    data class CreativeAdInfo(
        var ads: List<CreativeAdDisplayInfo>,
        var _type: String
    )

    data class CreativeAdDisplayInfo(
        var sub_header: String,
        var image: String,
        var hidden: Boolean,
        var _type: String,
        var description: String,
        var header: String,
        var creator_name: String
    )

    class LobbyInfo(
        var backgroundimage: String,
        var stage: String,
        _title: String,
        _activeDate: Date,
        lastModified: Date,
        _locale: String
    ) : FortBasicDataResponseBase(_title, _activeDate, lastModified, _locale)
}

open class FortBasicDataResponseBase(
    var _title: String,
    var _activeDate: Date,
    var lastModified: Date,
    var _locale: String
)

