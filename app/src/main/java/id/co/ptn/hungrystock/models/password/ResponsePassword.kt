package id.co.ptn.hungrystock.models.password

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.auth.ResponseAuthFailedDataV2
import id.co.ptn.hungrystock.models.auth.ResponseAuthSuccessDataV2

data class ResponsePassword (
    @SerializedName("response_code" ) var status : Int,
    @SerializedName("message" ) var message : String,
    @SerializedName("success_data"   ) var success_data : List<ResponsePasswordSuccessData>,
    @SerializedName("failed_rows"   ) var failed_rows   : Int?                  = null,
    @SerializedName("failed_data"   ) var failed_data   : List<ResponsePasswordFailedData> = mutableListOf()
)

data class ResponsePasswordSuccessData (
    @SerializedName("message" ) var message : String
)

data class ResponsePasswordFailedData (
    @SerializedName("messages" ) var messages : ArrayList<String> = arrayListOf()
)