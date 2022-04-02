package id.co.ptn.hungrystock.models.registration

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.User

data class ResponseRegister(
    @SerializedName("status" ) var status : String,
    @SerializedName("data"   ) var data   : ResponseRegisterData,
    @SerializedName("message"   ) var message   : String,
    @SerializedName("errors"   ) var errors   : ResponseRegisterError
)

data class ResponseRegisterError(
    @SerializedName("foto_profil") var foto_profil : List<String>,
    @SerializedName("email") var email : List<String>,
    @SerializedName("nomor_whatsapp") var nomor_whatsapp : List<String>,
)

data class ResponseRegisterData(
    @SerializedName("status"  ) var status  : String,
//    @SerializedName("user"  ) var user  : User,
    @SerializedName("role"  ) var role  : String,
    @SerializedName("token" ) var token : String
)