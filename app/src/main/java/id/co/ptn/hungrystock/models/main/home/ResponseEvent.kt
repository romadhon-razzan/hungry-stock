package id.co.ptn.hungrystock.models.main.home

import com.google.gson.annotations.SerializedName

data class
ResponseEvent (
    @SerializedName("status"  ) var status  : String,
    @SerializedName("message" ) var message : String,
    @SerializedName("data"    ) var data    : ResponseEventData
)

data class ResponseEventData (
    @SerializedName("headlineEvent" ) var headlineEvent : HeadlineEvent,
    @SerializedName("events"        ) var events        : Events
)

data class HeadlineEvent (

    @SerializedName("id"               ) var id             : Int?    = null, //
    @SerializedName("title"            ) var title          : String? = null, //
    @SerializedName("content"          ) var content        : String? = null, //
    @SerializedName("speaker"          ) var speaker        : String? = null, //
    @SerializedName("event_date"       ) var event_date      : String? = null, //
    @SerializedName("event_hour_start" ) var event_hour_start : String? = null, //
    @SerializedName("event_hour_end"   ) var event_hour_end   : String? = null, //
    @SerializedName("zoom_link"        ) var zoom_link       : String? = null, //
    @SerializedName("photo_url"        ) var photo_url       : String? = null, //

)

data class EventData (

    @SerializedName("id"               ) var id             : Int?              = null, //
    @SerializedName("title"            ) var title          : String?           = null, //
    @SerializedName("slug"             ) var slug           : String?           = null, //
    @SerializedName("content"          ) var content        : String?           = null, //
    @SerializedName("speaker"          ) var speaker        : String?           = null, //
    @SerializedName("event_date"       ) var event_date      : String?           = null, //
    @SerializedName("event_hour_start" ) var event_hour_start : String?           = null, //
    @SerializedName("event_hour_end"   ) var event_hour_end   : String?           = null, //
    @SerializedName("zoom_link"        ) var zoom_link       : String?           = null, //
    @SerializedName("photo_url"        ) var photo_url       : String?           = null, //

)

data class Events (

    @SerializedName("current_page"   ) var current_page  : Int?             = null, //
    @SerializedName("data"           ) var data         : List<EventData>, //
    @SerializedName("next_page_url"  ) var next_page_url  : String?          = null, //

)