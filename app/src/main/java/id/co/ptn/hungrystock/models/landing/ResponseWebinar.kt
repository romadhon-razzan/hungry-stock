package id.co.ptn.hungrystock.models.landing

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.onboard.ResponseOnboardData

data class ResponseWebinar (
    @SerializedName("response_code" ) var response_code : Int?            = null,
    @SerializedName("data"          ) var data         : List<ResponseWebinarData> = arrayListOf(),
    @SerializedName("offset"        ) var offset       : Int?            = null,
    @SerializedName("rows_per_page" ) var rows_per_page  : String?         = null,
    @SerializedName("total_pages"   ) var total_pages   : Int?            = null,
    @SerializedName("total_rows"    ) var total_rows    : Int?            = null,
    @SerializedName("message"       ) var message      : String?         = null
        )

data class ResponseWebinarData (
    @SerializedName("key"          ) var key         : String? = null,
    @SerializedName("code"         ) var code        : String? = null,
    @SerializedName("status_name"  ) var status_name  : String? = null,
    @SerializedName("title"        ) var title       : String? = null,
    @SerializedName("description"  ) var description : String? = null,
    @SerializedName("speakers"     ) var speakers    : String? = null,
    @SerializedName("date_from"    ) var date_from    : String? = null,
    @SerializedName("date_to"      ) var dateTo      : String? = null,
    @SerializedName("form_link"    ) var formLink    : String? = null,
    @SerializedName("youtube_link" ) var youtubeLink : String? = null,
    @SerializedName("price"        ) var price       : String? = null,
    @SerializedName("status_key"   ) var statusKey   : String? = null,
    @SerializedName("image_file"   ) var image_file   : String? = null
)