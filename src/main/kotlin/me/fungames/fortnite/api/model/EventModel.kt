package me.fungames.fortnite.api.model

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.*
import com.google.gson.annotations.Expose


data class LeaderboardsResponse (
    val gameId: String,
    val eventId: String,
    val eventWindowId: String,
    val page: Int,
    val totalPages: Int,
    val entries: List<LeaderboardEntry>
) {

    data class LeaderboardEntry(
        val gameId: String,
        val eventId: String,
        val eventWindowId: String,
        val teamId: String,
        val teamAccountIds: List<String>,
        val liveSessionId: String,
        val pointsEarned: Int,
        val score: Float,
        val rank: Int,
        val percentile: Float,
        val pointBreakdown: Map<String, PointBreakdown>,
        val sessionHistory: List<SessionHistory>,
        val tokens: List<String>,
        @field:Expose(serialize = false, deserialize = false)
        val modifiedTeamAccountIds: List<GameProfile>
    )

    data class SessionHistory(
        val sessionId: String,
        val endTime: Date,
        val trackedStats: Map<String, Int>
    )

    data class PointBreakdown(
        val timesAchieved: Int,
        val pointsEarned: Int
    )
}

data class AccountCompetitiveData(
    val gameId: String,
    val accountId: String,
    val tokens: List<String>,
    val teams: JsonObject,
    val pendingPayouts: List<String>,
    //{Hype:42}
    val persistentScores: JsonObject
)

data class EventDownloadResponse(
    val player: AccountCompetitiveData,
    val events: List<Event>,
    val templates: List<Template>,
    val scores: List<JsonElement>
) {
    data class Event(
        val gameId: String,
        val eventId: String,
        val regions: List<String>,
        val regionMappings: JsonObject,
        val platforms: List<String>,
        val platformMappings: JsonObject,
        val displayDataId: String,
        val announcementTime: Date,
        // JsonObject if not null
        val metadata: JsonElement,
        val eventWindows: List<EventWindow>,
        val beginTime: Date,
        val endTime: Date
    ) {

        fun findDisplayInfo(basicDataResponse: FortBasicDataResponse): FortBasicDataResponse.TournamentDisplayInfo? {
            for (tournamentDisplayInfo in basicDataResponse.tournamentinformation.tournament_info.tournaments) {
                if (displayDataId == tournamentDisplayInfo.tournament_display_id) {
                    return tournamentDisplayInfo
                }
            }

            return null
        }
    }

    data class EventWindow(
        val eventWindowId: String,
        val eventTemplateId: String,
        val countdownBeginTime: Date,
        val beginTime: Date,
        val endTime: Date,
        val blackoutPeriods: List<JsonElement>,
        val round: Int,
        val leaderboardId: String,
        val payoutDelay: Int,
        val isTBD: Boolean,
        // locked, public
        val visibility: String,
        val requireAllTokens: List<String>,
        val requireAnyTokens: List<String>,
        val requireNoneTokensCaller: List<String>,
        val requireAllTokensCaller: List<String>,
        val requireAnyTokensCaller: List<String>,
        val additionalRequirements: List<String>,
        val teammateEligibility: String,
        // JsonObject if not null
        val metadata: JsonElement
    ) {
        fun findTemplate(data: EventDownloadResponse): Template? {
            for (template in data.templates) {
                if (eventTemplateId == template.eventTemplateId) {
                    return template
                }
            }
            return null
        }
    }

    data class Template(
        val gameId: String,
        val eventTemplateId: String,
        val playlistId: String,
        val matchCap: Int,
        val useIndividualScores: Boolean,
        // null, Hype
        val persistentScoreId: String,
        val scoringRules: List<ScoringRule>,
        val tiebreakerFormula: TiebreakerFormula,
        val payoutTable: List<PayoutTableEntry>
    )

    data class ScoringRule(
        // PLACEMENT_STAT_INDEX, TEAM_ELIMS_STAT_INDEX
        val trackedStat: String,
        // lte, gte
        val matchRule: String,
        val rewardTiers: List<RewardTier>
    )

    data class RewardTier(
        val keyValue: Int,
        val pointsEarned: Int,
        val multiplicative: Boolean
    )

    data class TiebreakerFormula(
        val basePointBits: Int,
        val components: List<TiebreakerFormulaComponent>
    )

    data class TiebreakerFormulaComponent(
        // VICTORY_ROYALE_STAT, TEAM_ELIMS_STAT_INDEX, PLACEMENT_TIEBREAKER_STAT
        val trackedStat: String,
        val bits: Int,
        val multiplier: Float,
        // sum, avg
        val aggregation: String
    )

    data class PayoutTableEntry(
        // null, Hype
        val persistentScoreId: String,
        // rank, value
        val scoringType: String,
        val ranks: List<PayoutRank>
    )

    data class PayoutRank(
        val threshold: Float,
        val payouts: List<Payout>
    )

    data class Payout(
        val rewardType: String,
        val rewardMode: String,
        val value: String,
        val quantity: Int
    )
}