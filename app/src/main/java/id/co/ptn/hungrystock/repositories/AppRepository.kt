package id.co.ptn.hungrystock.repositories

import id.co.ptn.hungrystock.core.network.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun otp() =  apiHelper.otp()
    suspend fun authV2(username: String, password: String) =  apiHelper.authV2(username, password)
    suspend fun profile(param: String) =  apiHelper.profile(param)
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
    suspend fun registerStepTwo(
        s: RequestBody,
        fp: MultipartBody.Part,
        bb: MultipartBody.Part,
        nl: RequestBody,
        nw: RequestBody,
        e: RequestBody,
        tl: RequestBody,
        p: RequestBody,
        dm: RequestBody,
        li: RequestBody,
        pf: RequestBody,
        pd: RequestBody,
        pr: RequestBody
    ) = apiHelper.registerStepTwo(s,fp,bb,nl,nw, e, tl, p, dm, li, pf, pd, pr)
    suspend fun getEvent() =  apiHelper.getEvent()
    suspend fun getNextEvent(p: String) =  apiHelper.getNextEvent(p)
    suspend fun getLearningDetail(s: String) =  apiHelper.getLearningDetail(s)
    suspend fun getLearnings(k: String, c: String, y: String, m: String, ot: String) =  apiHelper.getLearnings(k, c, y, m, ot)
    suspend fun getNextLearnings(p: String, k: String, c: String, y: String, m: String, ot: String) =  apiHelper.getNextLearnings(p, k, c, y, m, ot)
    suspend fun getOnBoard() =  apiHelper.getOnboard()
    suspend fun getResearch(t: String, k: String, c: String, y: String, m: String, i: String) =  apiHelper.getResearch(t, k, c, y, m, i)
//    suspend fun searchName(s: String) =  apiHelper.searchName(s)
//    suspend fun detail(s: String) =  apiHelper.detail(s)
//    suspend fun getRecent() =  apiHelper.getRecent()
//    suspend fun filter(params: Map<String, String>) = apiHelper.filter(params)
}