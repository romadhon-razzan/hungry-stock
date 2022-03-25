package id.co.ptn.hungrystock.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("birth_date") var birthDate: String,
    @SerializedName("email_verified_at") var emailVerifiedAt: String,
    @SerializedName("two_factor_secret") var twoFactorSecret: String,
    @SerializedName("two_factor_recovery_codes") var twoFactorRecoveryCodes : String,
    @SerializedName("photo") var photo: String,
    @SerializedName("membership_end_at") var membershipEndAt: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String,
//    @SerializedName("roles") var roles: ArrayList<Roles>

)