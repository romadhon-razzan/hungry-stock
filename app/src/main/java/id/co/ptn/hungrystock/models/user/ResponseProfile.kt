package id.co.ptn.hungrystock.models.user

import com.google.gson.annotations.SerializedName

data class ResponseProfile (
    @SerializedName("response_code" ) var responseCode : Int?            = null,
    @SerializedName("data"          ) var data         : List<ResponseProfileData> = mutableListOf(),
    @SerializedName("offset"        ) var offset       : Int?            = null,
    @SerializedName("rows_per_page" ) var rowsPerPage  : String?         = null,
    @SerializedName("total_pages"   ) var totalPages   : Int?            = null,
    @SerializedName("total_rows"    ) var totalRows    : Int?            = null,
    @SerializedName("message"       ) var message      : String?         = null

)

data class ResponseProfileData (
    @SerializedName("request_id"            ) var requestId            : String? = null,
    @SerializedName("code"                  ) var code                 : String? = null,
    @SerializedName("name"                  ) var name                 : String? = null,
    @SerializedName("category_name"         ) var categoryName         : String? = null,
    @SerializedName("category_id"           ) var categoryId           : String? = null,
    @SerializedName("city_name"             ) var cityName             : String? = null,
    @SerializedName("city_id"               ) var cityId               : String? = null,
    @SerializedName("address"               ) var address              : String? = null,
    @SerializedName("zip_code"              ) var zipCode              : String? = null,
    @SerializedName("phone"                 ) var phone                : String? = null,
    @SerializedName("mobile"                ) var mobile               : String? = null,
    @SerializedName("fax"                   ) var fax                  : String? = null,
    @SerializedName("email"                 ) var email                : String? = null,
    @SerializedName("tax_id"                ) var taxId                : String? = null,
    @SerializedName("map_address"           ) var mapAddress           : String? = null,
    @SerializedName("latlng"                ) var latlng               : String? = null,
    @SerializedName("status_key"            ) var statusKey            : String? = null,
    @SerializedName("membership_level_key"  ) var membershipLevelKey   : String? = null,
    @SerializedName("membership_level_name" ) var membershipLevelName  : String? = null,
    @SerializedName("membership_exp_date"   ) var membershipExpDate    : Long?    = null,
    @SerializedName("activation_date"   ) var activation_date    : Long?    = null,
    @SerializedName("photo_file"              ) var photo_file             : String? = null,
    @SerializedName("domisili"              ) var domisili             : String? = null,
    @SerializedName("is_domisili_others"    ) var isDomisiliOthers     : String? = null,
    @SerializedName("experience_investment" ) var experienceInvestment : String? = null,
    @SerializedName("profession"            ) var profession           : String? = null,
    @SerializedName("is_profession_others"  ) var isProfessionOthers   : String? = null,
    @SerializedName("education"             ) var education            : String? = null,
    @SerializedName("is_education_others"   ) var isEducationOthers    : String? = null,
    @SerializedName("portfolio_investment"  ) var portfolioInvestment  : String? = null,
    @SerializedName("key"                   ) var key                  : String? = null,
    @SerializedName("status_name"           ) var statusName           : String? = null,
    @SerializedName("created_on"            ) var createdOn            : Int?    = null,
    @SerializedName("created_by"            ) var createdBy            : String? = null,
    @SerializedName("modified_on"           ) var modifiedOn           : Int?    = null,
    @SerializedName("modified_by"           ) var modifiedBy           : String? = null
)