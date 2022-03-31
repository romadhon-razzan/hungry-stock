package id.co.ptn.hungrystock.repositories

import id.co.ptn.hungrystock.core.network.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiHelper: ApiHelper) {
//    suspend fun getDrinkByFirstLetter(s: String) =  apiHelper.getDrinkByFirstLetter(s)
    suspend fun auth(nt: String, p: String) =  apiHelper.auth(nt, p)
    suspend fun resetPassword(e: String) =  apiHelper.resetPassword(e)
    suspend fun registerStepOne(
        s: RequestBody,
        fp: MultipartBody.Part,
        nl: RequestBody,
        tl: RequestBody,
        nw: RequestBody,
        e: RequestBody,
        p: RequestBody,
        cp: RequestBody
    ) =  apiHelper.registerStepOne(s,fp,nl,tl, nw, e, p, cp)
//    suspend fun getRandom() =  apiHelper.getRandom()
//    suspend fun searchName(s: String) =  apiHelper.searchName(s)
//    suspend fun detail(s: String) =  apiHelper.detail(s)
//    suspend fun getRecent() =  apiHelper.getRecent()
//    suspend fun filter(params: Map<String, String>) = apiHelper.filter(params)
}