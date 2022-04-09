package id.co.ptn.hungrystock.models.main.learning

import com.google.gson.annotations.SerializedName

data class SimiliarLearnings (
    @SerializedName("id"               ) var id             : Int?              = null,
    @SerializedName("title"            ) var title          : String?           = null,
    @SerializedName("slug"             ) var slug           : String?           = null,
    @SerializedName("photo"            ) var photo          : String?           = null,
    @SerializedName("content"          ) var content        : String?           = null,
    @SerializedName("speaker"          ) var speaker        : String?           = null,
    @SerializedName("event_date"       ) var eventDate      : String?           = null,
    @SerializedName("event_hour_start" ) var eventHourStart : String?           = null,
    @SerializedName("event_hour_end"   ) var eventHourEnd   : String?           = null,
    @SerializedName("zoom_link"        ) var zoomLink       : String?           = null,
    @SerializedName("is_published"     ) var isPublished    : Int?              = null,
    @SerializedName("video"            ) var video          : String?           = null,
    @SerializedName("other_file"       ) var otherFile      : String?           = null,
    @SerializedName("created_at"       ) var createdAt      : String?           = null,
    @SerializedName("updated_at"       ) var updatedAt      : String?           = null,
    @SerializedName("category"         ) var category       : String?           = null,
    @SerializedName("photo_url"        ) var photoUrl       : String?           = null,
    @SerializedName("video_url"        ) var videoUrl       : String?           = null,
    @SerializedName("other_file_url"   ) var otherFileUrl   : List<String>

)