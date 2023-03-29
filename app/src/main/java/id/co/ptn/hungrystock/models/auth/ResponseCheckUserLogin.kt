package id.co.ptn.hungrystock.models.auth

import com.google.gson.annotations.SerializedName

data class ResponseCheckUserLogin(
    @SerializedName("response_code" ) var responseCode : Int?                   = null,
    @SerializedName("success_rows"  ) var successRows  : Int?                   = null,
    @SerializedName("success_data"  ) var successData  : ArrayList<ResponseCheckSuccessData> = arrayListOf(),
    @SerializedName("failed_rows"   ) var failedRows   : Int?                   = null,
    @SerializedName("failed_data"   ) var failedData   : ArrayList<String>      = arrayListOf()
) {
    companion object {
        fun isLogin(data: ResponseCheckUserLogin?): Boolean {
            var result = false
            data?.successData?.forEach {successData ->
                result = successData.data.isLogin == 1 // artinya dia masih login di device / ip tersebut, jd boleh lanjut
            }
            return result
        }
    }
}
data class ResponseCheckSuccessData(
    @SerializedName("data"  ) var data  : ResponseCheckUserLoginData
)

data class ResponseCheckUserLoginData(
    @SerializedName("customer_id" ) var customerId : String? = null,
    @SerializedName("is_login"    ) var isLogin    : Int?    = null
)