package id.co.ptn.hungrystock.models.main.research

import com.google.gson.annotations.SerializedName

data class ResponseResearch (
    @SerializedName("response_code" ) var responseCode : Int?            = null,
    @SerializedName("data"          ) var data         : List<ResponseResearchData> = mutableListOf(),
    @SerializedName("offset"        ) var offset       : Int?            = null,
    @SerializedName("rows_per_page" ) var rowsPerPage  : String?         = null,
    @SerializedName("total_pages"   ) var totalPages   : Int?            = null,
    @SerializedName("total_rows"    ) var totalRows    : Int?            = null,
    @SerializedName("message"       ) var message      : String?         = null
        )

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