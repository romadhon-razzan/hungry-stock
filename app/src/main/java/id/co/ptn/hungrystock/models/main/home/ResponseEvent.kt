package id.co.ptn.hungrystock.models.main.home

import com.google.gson.annotations.SerializedName

data class ResponseEvent (
    @SerializedName("status"  ) var status  : String,
    @SerializedName("message" ) var message : String,
    @SerializedName("data"    ) var data    : ResponseEventData
)

data class ResponseEventData (
    @SerializedName("headlineEvent" ) var headlineEvent : HeadlineEvent,
    @SerializedName("events"        ) var events        : Events
)

data class HeadlineEvent (

    @SerializedName("id"               ) var id             : Int?    = null,
    @SerializedName("title"            ) var title          : String? = null,
    @SerializedName("slug"             ) var slug           : String? = null,
    @SerializedName("photo"            ) var photo          : String? = null,
    @SerializedName("content"          ) var content        : String? = null,
    @SerializedName("speaker"          ) var speaker        : String? = null,
    @SerializedName("event_date"       ) var event_date      : String? = null,
    @SerializedName("event_hour_start" ) var event_hour_start : String? = null,
    @SerializedName("event_hour_end"   ) var event_hour_end   : String? = null,
    @SerializedName("zoom_link"        ) var zoom_link       : String? = null,
    @SerializedName("is_published"     ) var is_published    : Int?    = null,
    @SerializedName("video"            ) var video          : String? = null,
    @SerializedName("other_file"       ) var other_file      : String? = null,
    @SerializedName("created_at"       ) var created_at      : String? = null,
    @SerializedName("updated_at"       ) var updated_at      : String? = null,
    @SerializedName("category"         ) var category       : String? = null,
    @SerializedName("photo_url"        ) var photo_url       : String? = null,
    @SerializedName("video_url"        ) var video_url       : String? = null,
    @SerializedName("other_file_url"   ) var other_file_url   : String? = null

)

data class EventData (

    @SerializedName("id"               ) var id             : Int?              = null,
    @SerializedName("title"            ) var title          : String?           = null,
    @SerializedName("slug"             ) var slug           : String?           = null,
    @SerializedName("photo"            ) var photo          : String?           = null,
    @SerializedName("content"          ) var content        : String?           = null,
    @SerializedName("speaker"          ) var speaker        : String?           = null,
    @SerializedName("event_date"       ) var event_date      : String?           = null,
    @SerializedName("event_hour_start" ) var event_hour_start : String?           = null,
    @SerializedName("event_hour_end"   ) var event_hour_end   : String?           = null,
    @SerializedName("zoom_link"        ) var zoom_link       : String?           = null,
    @SerializedName("is_published"     ) var is_published    : Int?              = null,
    @SerializedName("video"            ) var video          : String?           = null,
    @SerializedName("other_file"       ) var other_file      : String?           = null,
    @SerializedName("created_at"       ) var created_at      : String?           = null,
    @SerializedName("updated_at"       ) var updated_at      : String?           = null,
    @SerializedName("category"         ) var category       : String?           = null,
    @SerializedName("photo_url"        ) var photo_url       : String?           = null,
    @SerializedName("video_url"        ) var video_url       : String?           = null,
    @SerializedName("other_file_url"   ) var other_file_url   : List<String>

)

data class Events (

    @SerializedName("current_page"   ) var current_page  : Int?             = null,
    @SerializedName("data"           ) var data         : List<EventData>,
    @SerializedName("first_page_url" ) var first_page_url : String?          = null,
    @SerializedName("from"           ) var from         : Int?             = null,
    @SerializedName("last_page"      ) var last_page     : Int?             = null,
    @SerializedName("last_page_url"  ) var last_page_url  : String?          = null,
    @SerializedName("next_page_url"  ) var next_page_url  : String?          = null,
    @SerializedName("path"           ) var path         : String?          = null,
    @SerializedName("per_page"       ) var per_page      : Int?             = null,
    @SerializedName("prev_page_url"  ) var prev_page_url  : String?          = null,
    @SerializedName("to"             ) var to           : Int?             = null,
    @SerializedName("total"          ) var total        : Int?             = null

)