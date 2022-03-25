package id.co.ptn.hungrystock.models.auth

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.User

data class ResponseAuth(
    @SerializedName("status" ) var status : String,
    @SerializedName("data"   ) var data   : ResponseAuthData
)

data class ResponseAuthData(
    @SerializedName("status"  ) var status  : String,
    @SerializedName("user"  ) var user  : User,
    @SerializedName("role"  ) var role  : String,
    @SerializedName("token" ) var token : String
)