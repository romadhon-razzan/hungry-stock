package id.co.ptn.hungrystock.repositories

import id.co.ptn.hungrystock.core.network.ApiHelper
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiHelper: ApiHelper) {
//    suspend fun getDrinkByFirstLetter(s: String) =  apiHelper.getDrinkByFirstLetter(s)
    suspend fun auth(nt: String, p: String) =  apiHelper.auth(nt, p)
    suspend fun resetPassword(e: String) =  apiHelper.resetPassword(e)
//    suspend fun getRandom() =  apiHelper.getRandom()
//    suspend fun searchName(s: String) =  apiHelper.searchName(s)
//    suspend fun detail(s: String) =  apiHelper.detail(s)
//    suspend fun getRecent() =  apiHelper.getRecent()
//    suspend fun filter(params: Map<String, String>) = apiHelper.filter(params)
}