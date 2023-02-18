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
    @SerializedName("books"           ) var books           : List<Books>,
    @SerializedName("headlineWebinar" ) var headlineWebinar : HeadlineWebinar

)

data class Books (

    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("title"          ) var title         : String? = null,
    @SerializedName("link_tokopedia" ) var link_tokopedia : String? = null,
    @SerializedName("photo_url"      ) var photo_url      : String? = null

)

data class HeadlineWebinar (

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("title"         ) var title        : String? = null,
    @SerializedName("content"       ) var content      : String? = null,
    @SerializedName("speaker"       ) var speaker      : String? = null,
    @SerializedName("date_start"    ) var date_start    : String? = null,
    @SerializedName("date_end"      ) var date_end      : String? = null,
    @SerializedName("photo_url"     ) var photo_url     : String? = null

)