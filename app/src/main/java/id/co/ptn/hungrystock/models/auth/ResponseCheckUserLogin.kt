package id.co.ptn.hungrystock.models.auth

import android.app.Activity
import android.content.Context
import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.bases.dialogs.InfoDialog
import id.co.ptn.hungrystock.config.TOKEN

data class ResponseCheckUserLogin(
    @SerializedName("response_code" ) var responseCode : Int?                   = null,
    @SerializedName("success_rows"  ) var successRows  : Int?                   = null,
    @SerializedName("success_data"  ) var successData  : ArrayList<ResponseCheckSuccessData> = arrayListOf(),
    @SerializedName("failed_rows"   ) var failedRows   : Int?                   = null,
    @SerializedName("failed_data"   ) var failedData   : ArrayList<String>      = arrayListOf()
) {
    companion object {
        fun userLoginInAnotherDevice(data: ResponseCheckUserLogin?): Boolean {
            var result = false
            data?.successData?.forEach {successData ->
                result = successData.data.isLogin == 1
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