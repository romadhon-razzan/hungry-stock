package id.co.ptn.hungrystock.models.auth

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.User

data class ResponseOtp(
    @SerializedName("response_code" ) var status : Int,
    @SerializedName("data") var data   : String,
    @SerializedName("message") var message   : String
)