package id.co.ptn.hungrystock.models.onboard

import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.models.main.home.EventData

data class ResponseCodeOfConduct(
    @SerializedName("response_code" ) var responseCode : Int?            = null,
    @SerializedName("data"          ) var data         : ArrayList<ResponseCodeOfConductData> = arrayListOf(),
    @SerializedName("message"       ) var message      : String?         = null
)
data class ResponseCodeOfConductData(
    @SerializedName("title"             ) var title            : String? = null
)
