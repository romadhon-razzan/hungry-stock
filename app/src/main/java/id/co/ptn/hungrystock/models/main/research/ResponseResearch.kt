package id.co.ptn.hungrystock.models.main.research

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.main.home.ResponseEvents

data class ResponseResearch (
    @SerializedName("response_code" ) var responseCode : Int?            = null,
    @SerializedName("data"          ) var data         : List<ResponseResearchData> = mutableListOf(),
    @SerializedName("offset"        ) var offset       : Int?            = null,
    @SerializedName("rows_per_page" ) var rows_per_page  : String?         = null,
    @SerializedName("total_pages"   ) var total_pages   : Int?            = null,
    @SerializedName("total_rows"    ) var total_rows    : Int?            = null,
    @SerializedName("message"       ) var message      : String?         = null
        ){
    companion object {
        fun getNextPage(responseResearch: ResponseResearch): Int {
            val offset = responseResearch.offset ?: 0
            return offset + 1
        }
        fun canLoadNext(responseResearch: ResponseResearch): Boolean{
            val offset = responseResearch.offset ?: 0
            val totalPages = responseResearch.total_pages ?: 0
            return offset < totalPages
        }
    }
}

data class ResponseResearchData (
    @SerializedName("key"           ) var key          : String?  = null,
    @SerializedName("code"          ) var code         : String?  = null,
    @SerializedName("status_name"   ) var statusName   : String?  = null,
    @SerializedName("created_on"    ) var createdOn    : Long?     = null,
    @SerializedName("date"          ) var date         : Long?     = null,
    @SerializedName("title"         ) var title        : String?  = null,
    @SerializedName("category_name" ) var categoryName : String?  = null,
    @SerializedName("category_id"   ) var categoryId   : String?  = null,
    @SerializedName("type_name"     ) var typeName     : String?  = null,
    @SerializedName("type_id"       ) var typeId       : String?  = null,
    @SerializedName("status_key"    ) var statusKey    : String?  = null,
    @SerializedName("image_file"    ) var imageFile    : String?  = null,
    @SerializedName("file"          ) var file         : String?  = null
)