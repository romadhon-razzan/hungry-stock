package id.co.ptn.hungrystock.models

import com.google.gson.annotations.SerializedName

data class Links (

    @SerializedName("url"    ) var url    : String?  = null,
    @SerializedName("label"  ) var label  : String?  = null,
    @SerializedName("active" ) var active : Boolean? = null

)