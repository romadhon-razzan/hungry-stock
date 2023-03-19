package id.co.ptn.hungrystock.core.network

import com.google.gson.JsonObject
import id.co.ptn.hungrystock.models.auth.ResponseAuth
import id.co.ptn.hungrystock.models.auth.ResponseAuthV2
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.landing.ResponseBooks
import id.co.ptn.hungrystock.models.landing.ResponseWebinar
import id.co.ptn.hungrystock.models.main.home.ResponseEvent
import id.co.ptn.hungrystock.models.main.home.ResponseEvents
import id.co.ptn.hungrystock.models.main.home.ResponseEventsRelated
import id.co.ptn.hungrystock.models.main.learning.ResponseLearning
import id.co.ptn.hungrystock.models.main.learning.ResponseLearningDetail
import id.co.ptn.hungrystock.models.main.research.ResponseResearch
import id.co.ptn.hungrystock.models.onboard.ResponseCodeOfConduct
import id.co.ptn.hungrystock.models.onboard.ResponseOnboard
import id.co.ptn.hungrystock.models.password.ResponsePassword
import id.co.ptn.hungrystock.models.reference.ResponseEventCategories
import id.co.ptn.hungrystock.models.reference.ResponseResearchCategories
import id.co.ptn.hungrystock.models.reference.ResponseResearchCategoriesData
import id.co.ptn.hungrystock.models.registration.ResponseRegister
import id.co.ptn.hungrystock.models.user.ResponseProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: Services) : ApiHelper {
    override suspend fun otp(): Response<ResponseOtp> = apiService.otp()
    override suspend fun authV2(username: String, password: String): Response<ResponseAuthV2> = apiService.authV2(username, password)
    override suspend fun profile(param: String): Response<ResponseProfile> = apiService.profile(param)
    override suspend fun events(param: String): Response<ResponseEvents> = apiService.events(param)
    override suspend fun eventsRelated(param: String): Response<ResponseEventsRelated> = apiService.eventsRelated(param)
    override suspend fun eventCategories(): Response<ResponseEventCategories> = apiService.eventCategories()
    override suspend fun researchCategories(): Response<ResponseResearchCategories> = apiService.researchCategories()
    override suspend fun getResearch(param: String): Response<ResponseResearch> = apiService.getResearch(param)
    override suspend fun books(): Response<ResponseBooks> = apiService.books()
    override suspend fun webinar(): Response<ResponseWebinar> = apiService.webinar()
    override suspend fun codeOfConduct(): Response<ResponseCodeOfConduct> = apiService.codeOfConduct()

    override suspend fun auth(nt: String, p: String): Response<ResponseAuth> = apiService.auth(nt, p)
    override suspend fun resetPassword(e: String): Response<ResponsePassword> = apiService.resetPassword(e)
    override suspend fun registerStepOne(
        s: RequestBody,
        fp: MultipartBody.Part,
        nl: RequestBody,
        tl: RequestBody,
        nw: RequestBody,
        e: RequestBody,
        p: RequestBody,
        cp: RequestBody
    ): Response<ResponseRegister> = apiService.registerStepOne(s,fp,nl,tl,nw,e,p,cp)

    override suspend fun registerStepTwo(
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
    ): Response<ResponseRegister> = apiService.registerStepTwo(s,fp,bb,nl,nw,e,tl,p,dm,li,pf,pd,pr)

    override suspend fun getEvent(): Response<ResponseEvent> = apiService.getEvent()
    override suspend fun getNextEvent(p: String): Response<ResponseEvent> = apiService.getNextEvent(p)
    override suspend fun getLearningDetail(s: String): Response<ResponseLearningDetail> = apiService.getLearningDetail(s)
    override suspend fun getLearnings(k: String, c: String, y: String, m: String, ot: String): Response<ResponseLearning> = apiService.getLearnings(k, c, y, m, ot)
    override suspend fun getNextLearnings(p: String, k: String, c: String, y: String, m: String, ot: String): Response<ResponseLearning> = apiService.getNextLearnings(p,k, c, y, m, ot)
    override suspend fun getOnboard(): Response<ResponseOnboard> = apiService.getOnboard()
//    override suspend fun getRandom(): Response<DrinkResponse> = apiService.getRandom()
//    override suspend fun searchName(s: String): Response<DrinkResponse> = apiService.searchName(s)
//    override suspend fun detail(s: String): Response<DrinkResponse> = apiService.detail(s)
//    override suspend fun getRecent(): Response<DrinkResponse> = apiService.getRecent()
//    override suspend fun getCategories(): Response<CategoryResponse> = apiService.getCategories()
//    override suspend fun getGlasses(): Response<GlassesResponse> = apiService.getGlasses()
//    override suspend fun getIngredients(): Response<IngredientResponse> = apiService.getIngredients()
//    override suspend fun getAlcoholic(): Response<AlcoholicResponse> = apiService.getAlcoholic()
//    override suspend fun filter(params: Map<String, String>): Response<DrinkResponse> = apiService.filter(params)
}