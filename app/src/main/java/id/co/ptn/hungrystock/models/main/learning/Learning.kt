package id.co.ptn.hungrystock.models.main.learning

import com.google.gson.annotations.SerializedName

data class Learning(
    @SerializedName("id"               ) var id             : Int?              = null, //
    @SerializedName("title"            ) var title          : String?           = null, //
    @SerializedName("slug"             ) var slug           : String?           = null, //
    @SerializedName("content"          ) var content        : String?           = null, //
    @SerializedName("speaker"          ) var speaker        : String?           = null, //
    @SerializedName("event_date"       ) var event_date      : String?           = null, //
    @SerializedName("event_hour_start" ) var event_hour_start : String?           = null, //
    @SerializedName("event_hour_end"   ) var event_hour_end   : String?           = null, //
    @SerializedName("photo_url"        ) var photo_url       : String?           = null, //
    @SerializedName("video_url"        ) var video_url       : String?           = null, //

)