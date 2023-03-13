package id.co.ptn.hungrystock.models.main.home

import com.google.gson.annotations.SerializedName

data class
ResponseEvents (
    @SerializedName("status"  ) var status  : String,
    @SerializedName("message" ) var message : String,
    @SerializedName("data"    ) var data    : List<ResponseEventsData>,
    @SerializedName("offset"        ) var offset       : Int?            = null,
    @SerializedName("rows_per_page" ) var rows_per_page  : String?         = null,
    @SerializedName("total_pages"   ) var total_pages   : Int?            = null,
    @SerializedName("total_rows"    ) var total_rows    : Int?            = null
)

data class ResponseEventsData (
    @SerializedName("key"           ) var key          : String?  = null,
    @SerializedName("code"          ) var code         : String?  = null,
    @SerializedName("status_name"   ) var status_name   : String?  = null,
    @SerializedName("created_on"    ) var created_on    : Int?     = null,
    @SerializedName("created_by"    ) var created_by    : String?  = null,
//    @SerializedName("modified_on"   ) var modified_on   : Boolean? = null,
    @SerializedName("modified_by"   ) var modified_by   : String?  = null,
    @SerializedName("title"         ) var title        : String?  = null,
    @SerializedName("description"   ) var description  : String?  = null,
    @SerializedName("speakers"      ) var speakers     : String?  = null,
    @SerializedName("date_from"     ) var date_from     : Long?     = null,
    @SerializedName("date_to"       ) var date_to       : Int?     = null,
    @SerializedName("is_free"       ) var is_free       : String?  = null,
    @SerializedName("zoom_link"     ) var zoom_link     : String?  = null,
    @SerializedName("category_name" ) var category_name : String?  = null,
    @SerializedName("category_id"   ) var category_id   : String?  = null,
    @SerializedName("status_key"    ) var status_key    : String?  = null,
    @SerializedName("image_file"    ) var image_file    : String?  = null,
    @SerializedName("video_file"    ) var video_file    : String?  = null,
    @SerializedName("pdf_file"      ) var pdf_file      : String?  = null
)