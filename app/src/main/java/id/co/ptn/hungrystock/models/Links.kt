package id.co.ptn.hungrystock.models

import com.google.gson.annotations.SerializedName

data class Links (

    @SerializedName("url"    ) var url    : String?  = null,
    @SerializedName("label"  ) var label  : String?  = null,
    @SerializedName("active" ) var active : Boolean? = null

) {
    companion object {
        val previous = "previous"
        val next = "next"
        fun previousPage(value: String): String {
            return (value.toInt() - 1).toString()
        }

        fun nextPage(value: String): String {
            return (value.toInt() + 1).toString()
        }
    }
}