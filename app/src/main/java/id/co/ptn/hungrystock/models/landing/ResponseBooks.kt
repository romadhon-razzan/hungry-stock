package id.co.ptn.hungrystock.models.landing

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.onboard.ResponseOnboardData

data class ResponseBooks (
    @SerializedName("response_code" ) var responseCode : Int?            = null,
    @SerializedName("data"          ) var data         : List<ResponseBooksData> = arrayListOf(),
    @SerializedName("offset"        ) var offset       : Int?            = null,
    @SerializedName("rows_per_page" ) var rowsPerPage  : String?         = null,
    @SerializedName("total_pages"   ) var totalPages   : Int?            = null,
    @SerializedName("total_rows"    ) var totalRows    : Int?            = null,
    @SerializedName("message"       ) var message      : String?         = null
        )

data class ResponseBooksData (
    @SerializedName("key"                    ) var key                  : String?  = null,
    @SerializedName("code"                   ) var code                 : String?  = null,
    @SerializedName("status_name"            ) var status_name           : String?  = null,
    @SerializedName("barcode"                ) var barcode              : String?  = null,
    @SerializedName("name"                   ) var name                 : String?  = null,
    @SerializedName("condition"              ) var condition            : String?  = null,
    @SerializedName("category_id"            ) var categoryId           : String?  = null,
    @SerializedName("category_name"          ) var categoryName         : String?  = null,
    @SerializedName("weight_unit_name"       ) var weightUnitName       : String?  = null,
    @SerializedName("weight_unit_id"         ) var weightUnitId         : String?  = null,
    @SerializedName("base_unit_name"         ) var baseUnitName         : String?  = null,
    @SerializedName("base_unit_id"           ) var baseUnitId           : String?  = null,
    @SerializedName("parent_id"              ) var parentId             : String?  = null,
    @SerializedName("brand"                  ) var brand                : String?  = null,
    @SerializedName("brand_id"               ) var brandId              : String?  = null,
    @SerializedName("selling_price"          ) var sellingPrice         : String?  = null,
    @SerializedName("original_selling_price" ) var originalSellingPrice : String?  = null,
    @SerializedName("disc_percentage"        ) var discPercentage       : String?  = null,
    @SerializedName("has_disc"               ) var hasDisc              : String?  = null,
    @SerializedName("max_stock"              ) var maxStock             : String?  = null,
    @SerializedName("weight"                 ) var weight               : String?  = null,
    @SerializedName("short_description"      ) var shortDescription     : String?  = null,
    @SerializedName("qtyonhand"              ) var qtyonhand            : String?  = null,
    @SerializedName("min_stock"              ) var minStock             : String?  = null,
    @SerializedName("tag"                    ) var tag                  : String?  = null,
    @SerializedName("status_key"             ) var statusKey            : String?  = null,
    @SerializedName("image_file"             ) var imageFile            : String?  = null,
    @SerializedName("tokopedia_url"          ) var tokopediaUrl         : String?  = null
)