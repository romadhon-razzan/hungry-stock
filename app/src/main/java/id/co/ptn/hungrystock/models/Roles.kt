package id.co.ptn.hungrystock.models

import com.google.gson.annotations.SerializedName

data class Roles (

    @SerializedName("id"         ) var id        : Int,
    @SerializedName("name"       ) var name      : String,
    @SerializedName("guard_name" ) var guardName : String,
    @SerializedName("created_at" ) var createdAt : String,
    @SerializedName("updated_at" ) var updatedAt : String

)