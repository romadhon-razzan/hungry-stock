package id.co.ptn.hungrystock.models

import android.util.Log
import androidx.fragment.app.FragmentManager
import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.ui.profile.dialogs.ExpiredFragmentDialog
import id.co.ptn.hungrystock.utils.*

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

) {
    companion object {
        fun isExpired(fragmentManager: FragmentManager, membership_end_at: String): Boolean {
            if (membership_end_at.isEmpty())
                return true

            val date1 = stringToDate(iso8601, membership_end_at)
            val result = date1AfterDate2(dateToString(yyyyMMdd_HHmmss, date1), currentDateString(yyyyMMdd_HHmmss), yyyyMMdd_HHmmss)
            if (result){
                val dialog = ExpiredFragmentDialog.newInstance()
                dialog.show(fragmentManager,"dialog_expired")
            }
            return result
        }
    }
}