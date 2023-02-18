package id.co.ptn.hungrystock.models.main.learning

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.Links

data class ResponseLearning (
    @SerializedName("status"  ) var status  : String? = null, //
    @SerializedName("message" ) var message : String? = null, //
    @SerializedName("data"    ) var data    : ResponseLearningData //

)

data class ResponseLearningData(
    @SerializedName("learnings" ) var learnings : Learnings //
)

data class Learnings (

    @SerializedName("current_page"   ) var current_page  : Int?             = null, //
    @SerializedName("data"           ) var data         : List<Learning>, //
    @SerializedName("last_page"      ) var last_page     : Int?             = null, //
    @SerializedName("next_page_url"  ) var next_page_url  : String?          = null, //
    @SerializedName("links"  ) var links  : List<Links>, //

)