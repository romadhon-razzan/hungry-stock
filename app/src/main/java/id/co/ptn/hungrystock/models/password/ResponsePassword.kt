package id.co.ptn.hungrystock.models.password

import com.google.gson.annotations.SerializedName

data class ResponsePassword (
    @SerializedName("status") var status: String,
    @SerializedName("message") var message: String,
)