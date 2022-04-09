package id.co.ptn.hungrystock.models.main.learning

import com.google.gson.annotations.SerializedName

data class ResponseLearningDetail (
    @SerializedName("status"  ) var status  : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : LearningDetailData
)

data class LearningDetailData (
    @SerializedName("learning"          ) var learning          : Learning,
    @SerializedName("similiarLearnings" ) var similiarLearnings : List<SimiliarLearnings>
)