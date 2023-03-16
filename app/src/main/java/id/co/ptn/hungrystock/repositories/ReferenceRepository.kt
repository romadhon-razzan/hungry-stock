package id.co.ptn.hungrystock.repositories

import id.co.ptn.hungrystock.core.network.ApiHelper
import javax.inject.Inject

class ReferenceRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun otp() =  apiHelper.otp()
    suspend fun getEventCategories() =  apiHelper.eventCategories()
    suspend fun getResearchCategories() =  apiHelper.researchCategories()
}