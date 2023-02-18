package id.co.ptn.hungrystock.models
import androidx.fragment.app.FragmentManager
import com.google.gson.annotations.SerializedName
import id.co.ptn.hungrystock.ui.profile.dialogs.ExpiredFragmentDialog
import id.co.ptn.hungrystock.utils.*

data class User (
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("email_verified_at") var email_verified_at: String,
    @SerializedName("photo") var photo: String,
    @SerializedName("membership_end_at") var membership_end_at: String,

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