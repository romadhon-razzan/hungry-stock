package id.co.ptn.hungrystock.models.reference

import com.google.gson.annotations.SerializedName

data class ResponseEventCategories (
    @SerializedName("response_code" ) var response_code : Int?            = null,
    @SerializedName("data"          ) var data         : List<ResponseEventCategoriesData> = mutableListOf(),
    @SerializedName("offset"        ) var offset       : Int?            = null,
    @SerializedName("total_pages"   ) var total_pages   : Int?            = null,
    @SerializedName("message"       ) var message      : String?         = null
)

data class ResponseEventCategoriesData (

    @SerializedName("key"         ) var key        : String? = null,
    @SerializedName("code"        ) var code       : String? = null,
    @SerializedName("status_name" ) var status_name : String? = null,
    @SerializedName("created_on"  ) var created_on  : Int?    = null,
    @SerializedName("created_by"  ) var created_by  : String? = null,
    @SerializedName("modified_on" ) var modified_on : Int?    = null,
    @SerializedName("modified_by" ) var modified_by : String? = null,
    @SerializedName("name"        ) var name       : String? = null,
    @SerializedName("parent_id"   ) var parent_id   : String? = null,
    @SerializedName("status_key"  ) var status_key  : String? = null,
    @SerializedName("order_list"  ) var order_list  : String? = null,
    @SerializedName("is_leaf"     ) var is_leaf     : String? = null

)