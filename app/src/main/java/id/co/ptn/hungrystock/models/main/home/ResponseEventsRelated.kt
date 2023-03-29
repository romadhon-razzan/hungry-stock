package id.co.ptn.hungrystock.models.main.home

import com.google.gson.annotations.SerializedName

data class
ResponseEventsRelated (
    @SerializedName("response_code" ) var responseCode : Int?                   = null,
    @SerializedName("success_rows"  ) var successRows  : Int?                   = null,
    @SerializedName("success_data"  ) var successData  : ArrayList<ResponseEventsRelatedData> = arrayListOf(),
    @SerializedName("failed_rows"   ) var failedRows   : Int?                   = null,
    @SerializedName("failed_data"   ) var failedData   : ArrayList<String>      = arrayListOf()
)

data class ResponseEventsRelatedData (
    @SerializedName("data" ) var data : ArrayList<ResponseEventsData> = arrayListOf()
)