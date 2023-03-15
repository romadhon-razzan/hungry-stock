package id.co.ptn.hungrystock.repositories

import id.co.ptn.hungrystock.core.network.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class EventRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun otp() =  apiHelper.otp()
    suspend fun getEvent(param: String) =  apiHelper.events(param)

}