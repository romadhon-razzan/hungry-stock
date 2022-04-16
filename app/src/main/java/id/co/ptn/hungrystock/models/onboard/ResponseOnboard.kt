package id.co.ptn.hungrystock.models.onboard

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.main.home.EventData

data class ResponseOnboard(
    @SerializedName("status"  ) var status  : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ResponseOnboardData
)

data class ResponseOnboardData (

    @SerializedName("latestEvent"     ) var latestEvent     : EventData,
    @SerializedName("headlineBook"    ) var headlineBook    : HeadlineBook,
    @SerializedName("books"           ) var books           : List<Books>,
    @SerializedName("headlineWebinar" ) var headlineWebinar : HeadlineWebinar

)

data class HeadlineBook (

    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("title"          ) var title         : String? = null,
    @SerializedName("slug"           ) var slug          : String? = null,
    @SerializedName("photo"          ) var photo         : String? = null,
    @SerializedName("link_tokopedia" ) var linkTokopedia : String? = null,
    @SerializedName("author"         ) var author        : String? = null,
    @SerializedName("description"    ) var description   : String? = null,
    @SerializedName("is_banner"      ) var isBanner      : Int?    = null,
    @SerializedName("is_published"   ) var isPublished   : Int?    = null,
    @SerializedName("created_at"     ) var createdAt     : String? = null,
    @SerializedName("updated_at"     ) var updatedAt     : String? = null,
    @SerializedName("photo_url"      ) var photoUrl      : String? = null

)

data class Books (

    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("title"          ) var title         : String? = null,
    @SerializedName("slug"           ) var slug          : String? = null,
    @SerializedName("photo"          ) var photo         : String? = null,
    @SerializedName("link_tokopedia" ) var linkTokopedia : String? = null,
    @SerializedName("author"         ) var author        : String? = null,
    @SerializedName("description"    ) var description   : String? = null,
    @SerializedName("is_banner"      ) var isBanner      : Int?    = null,
    @SerializedName("is_published"   ) var isPublished   : Int?    = null,
    @SerializedName("created_at"     ) var createdAt     : String? = null,
    @SerializedName("updated_at"     ) var updatedAt     : String? = null,
    @SerializedName("photo_url"      ) var photoUrl      : String? = null

)

data class HeadlineWebinar (

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("title"         ) var title        : String? = null,
    @SerializedName("slug"          ) var slug         : String? = null,
    @SerializedName("photo"         ) var photo        : String? = null,
    @SerializedName("content"       ) var content      : String? = null,
    @SerializedName("speaker"       ) var speaker      : String? = null,
    @SerializedName("date_start"    ) var dateStart    : String? = null,
    @SerializedName("date_end"      ) var dateEnd      : String? = null,
    @SerializedName("form_link"     ) var formLink     : String? = null,
    @SerializedName("is_published"  ) var isPublished  : Int?    = null,
    @SerializedName("created_at"    ) var createdAt    : String? = null,
    @SerializedName("updated_at"    ) var updatedAt    : String? = null,
    @SerializedName("youtube_link"  ) var youtubeLink  : String? = null,
    @SerializedName("photo_url"     ) var photoUrl     : String? = null,
    @SerializedName("embed_youtube" ) var embedYoutube : String? = null

)