package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ZoomMeetingsJoinResponse(
    @SerialName("uuid") var uuid: String? = null,
    @SerialName("id") var id: Long? = null,
    @SerialName("host_id") var hostId: String? = null,
    @SerialName("host_email") var hostEmail: String? = null,
    @SerialName("assistant_id") var assistantId: String? = null,
    @SerialName("topic") var topic: String? = null,
    @SerialName("type") var type: Int? = null,
    @SerialName("status") var status: String? = null,
    @SerialName("timezone") var timezone: String? = null,
    @SerialName("agenda") var agenda: String? = null,
    @SerialName("created_at") var createdAt: String? = null,
    @SerialName("start_url") var startUrl: String? = null,
    @SerialName("join_url") var joinUrl: String? = null,
    @SerialName("password") var password: String? = null,
    @SerialName("h323_password") var h323Password: String? = null,
    @SerialName("pstn_password") var pstnPassword: String? = null,
    @SerialName("encrypted_password") var encryptedPassword: String? = null,
    @SerialName("occurrences") var occurrences: ArrayList<Occurrences> = arrayListOf(),
    @SerialName("settings") var settings: Settings? = Settings(),
    @SerialName("recurrence") var recurrence: Recurrence? = Recurrence(),
    @SerialName("creation_source") var creationSource: String? = null,
    @SerialName("pre_schedule") var preSchedule: Boolean? = null,
) {
    @Serializable
    data class Occurrences(

        @SerialName("occurrence_id") var occurrenceId: String? = null,
        @SerialName("start_time") var startTime: String? = null,
        @SerialName("duration") var duration: Int? = null,
        @SerialName("status") var status: String? = null,

        )

    @Serializable
    data class Settings(

        @SerialName("host_video") var hostVideo: Boolean? = null,
        @SerialName("participant_video") var participantVideo: Boolean? = null,
        @SerialName("cn_meeting") var cnMeeting: Boolean? = null,
        @SerialName("in_meeting") var inMeeting: Boolean? = null,
        @SerialName("join_before_host") var joinBeforeHost: Boolean? = null,
        @SerialName("jbh_time") var jbhTime: Int? = null,
        @SerialName("mute_upon_entry") var muteUponEntry: Boolean? = null,
        @SerialName("watermark") var watermark: Boolean? = null,
        @SerialName("use_pmi") var usePmi: Boolean? = null,
        @SerialName("approval_type") var approvalType: Int? = null,
        @SerialName("audio") var audio: String? = null,
        @SerialName("auto_recording") var autoRecording: String? = null,
        @SerialName("enforce_login") var enforceLogin: Boolean? = null,
        @SerialName("enforce_login_domains") var enforceLoginDomains: String? = null,
        @SerialName("alternative_hosts") var alternativeHosts: String? = null,
        @SerialName("alternative_host_update_polls") var alternativeHostUpdatePolls: Boolean? = null,
        @SerialName("close_registration") var closeRegistration: Boolean? = null,
        @SerialName("show_share_button") var showShareButton: Boolean? = null,
        @SerialName("allow_multiple_devices") var allowMultipleDevices: Boolean? = null,
        @SerialName("registrants_confirmation_email") var registrantsConfirmationEmail: Boolean? = null,
        @SerialName("waiting_room") var waitingRoom: Boolean? = null,
        @SerialName("request_permission_to_unmute_participants") var requestPermissionToUnmuteParticipants: Boolean? = null,
        @SerialName("global_dial_in_countries") var globalDialInCountries: ArrayList<String> = arrayListOf(),
        @SerialName("global_dial_in_numbers") var globalDialInNumbers: ArrayList<GlobalDialInNumbers> = arrayListOf(),
        @SerialName("registrants_email_notification") var registrantsEmailNotification: Boolean? = null,
        @SerialName("meeting_authentication") var meetingAuthentication: Boolean? = null,
        @SerialName("encryption_type") var encryptionType: String? = null,
        @SerialName("approved_or_denied_countries_or_regions") var approvedOrDeniedCountriesOrRegions: ApprovedOrDeniedCountriesOrRegions? = ApprovedOrDeniedCountriesOrRegions(),
        @SerialName("breakout_room") var breakoutRoom: BreakoutRoom? = BreakoutRoom(),
        @SerialName("internal_meeting") var internalMeeting: Boolean? = null,
        @SerialName("continuous_meeting_chat") var continuousMeetingChat: ContinuousMeetingChat? = ContinuousMeetingChat(),
        @SerialName("participant_focused_meeting") var participantFocusedMeeting: Boolean? = null,
        @SerialName("push_change_to_calendar") var pushChangeToCalendar: Boolean? = null,
        @SerialName("resources") var resources: ArrayList<String> = arrayListOf(),
        @SerialName("auto_start_meeting_summary") var autoStartMeetingSummary: Boolean? = null,
        @SerialName("auto_start_ai_companion_questions") var autoStartAiCompanionQuestions: Boolean? = null,
        @SerialName("allow_host_control_participant_mute_state") var allowHostControlParticipantMuteState: Boolean? = null,
        @SerialName("alternative_hosts_email_notification") var alternativeHostsEmailNotification: Boolean? = null,
        @SerialName("show_join_info") var showJoinInfo: Boolean? = null,
        @SerialName("device_testing") var deviceTesting: Boolean? = null,
        @SerialName("focus_mode") var focusMode: Boolean? = null,
        @SerialName("meeting_invitees") var meetingInvitees: ArrayList<String> = arrayListOf(),
        @SerialName("private_meeting") var privateMeeting: Boolean? = null,
        @SerialName("email_notification") var emailNotification: Boolean? = null,
        @SerialName("host_save_video_order") var hostSaveVideoOrder: Boolean? = null,
        @SerialName("sign_language_interpretation") var signLanguageInterpretation: SignLanguageInterpretation? = SignLanguageInterpretation(),
        @SerialName("email_in_attendee_report") var emailInAttendeeReport: Boolean? = null,

        ) {
        @Serializable
        data class GlobalDialInNumbers(

            @SerialName("country_name") var countryName: String? = null,
            @SerialName("number") var number: String? = null,
            @SerialName("type") var type: String? = null,
            @SerialName("country") var country: String? = null,

            )

        @Serializable
        data class ApprovedOrDeniedCountriesOrRegions(

            @SerialName("enable") var enable: Boolean? = null,

            )

        @Serializable
        data class BreakoutRoom(

            @SerialName("enable") var enable: Boolean? = null,

            )

        @Serializable
        data class ContinuousMeetingChat(

            @SerialName("enable") var enable: Boolean? = null,
            @SerialName("auto_add_invited_external_users") var autoAddInvitedExternalUsers: Boolean? = null,
            @SerialName("auto_add_meeting_participants") var autoAddMeetingParticipants: Boolean? = null,

            )

        @Serializable
        data class SignLanguageInterpretation(

            @SerialName("enable") var enable: Boolean? = null,

            )
    }

    @Serializable
    data class Recurrence(

        @SerialName("type") var type: Int? = null,
        @SerialName("repeat_interval") var repeatInterval: Int? = null,
        @SerialName("end_date_time") var endDateTime: String? = null,

        )
}
