package id.co.ptn.hungrystock.models.landing

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.onboard.ResponseOnboardData

data class ResponseBooks (
    @SerializedName("status"  ) var status  : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ResponseOnboardData
        )