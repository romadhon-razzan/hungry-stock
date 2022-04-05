package id.co.ptn.hungrystock.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("birth_date") var birthDate: String,
    @SerializedName("email_verified_at") var email_verified_at: String,
    @SerializedName("two_factor_secret") var two_factor_secret: String,
    @SerializedName("two_factor_recovery_codes") var two_factor_recovery_codes : String,
    @SerializedName("photo") var photo: String,
    @SerializedName("membership_end_at") var membership_end_at: String,
    @SerializedName("created_at") var created_at: String,
    @SerializedName("updated_at") var updated_at: String,
//    @SerializedName("roles") var roles: ArrayList<Roles>

)