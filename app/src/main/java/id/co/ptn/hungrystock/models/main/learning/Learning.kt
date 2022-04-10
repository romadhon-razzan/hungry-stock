package id.co.ptn.hungrystock.models.main.learning

import com.google.gson.annotations.SerializedName

data class Learning(
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