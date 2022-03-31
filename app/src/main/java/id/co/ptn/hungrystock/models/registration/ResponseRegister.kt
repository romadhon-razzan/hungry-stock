package id.co.ptn.hungrystock.models.registration

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.User

data class ResponseRegister(
    @SerializedName("status" ) var status : String,
    @SerializedName("data"   ) var data   : ResponseRegisterData
)

data class ResponseRegisterData(
    @SerializedName("status"  ) var status  : String,
    @SerializedName("user"  ) var user  : User,
    @SerializedName("role"  ) var role  : String,
    @SerializedName("token" ) var token : String
)