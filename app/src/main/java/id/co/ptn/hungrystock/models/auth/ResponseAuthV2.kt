package id.co.ptn.hungrystock.models.auth

import com.google.gson.annotations.SerializedName

data class ResponseAuthV2(
    @SerializedName("response_code" ) var status : Int,
    @SerializedName("message" ) var message : String,
    @SerializedName("success_data"   ) var success_data : List<ResponseAuthSuccessDataV2>
)
data class ResponseAuthSuccessDataV2(
    @SerializedName("data"  ) var data  : ResponseAuthDataV2
)
data class ResponseAuthDataV2(
    @SerializedName("pkey"  ) var pkey  : String,
    @SerializedName("code"  ) var code  : String,
    @SerializedName("user_full_name"  ) var user_full_name  : String,
    @SerializedName("username" ) var username : String
)