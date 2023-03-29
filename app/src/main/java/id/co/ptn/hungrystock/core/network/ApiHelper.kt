package id.co.ptn.hungrystock.core.network

import id.co.ptn.hungrystock.models.auth.ResponseAuth
import id.co.ptn.hungrystock.models.auth.ResponseAuthV2
import id.co.ptn.hungrystock.models.auth.ResponseCheckUserLogin
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
import id.co.ptn.hungrystock.models.registration.ResponseRegister
import id.co.ptn.hungrystock.models.user.ResponseProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface ApiHelper {
//    suspend fun getDrinkByFirstLetter(s: String): Response<DrinkResponse>
    suspend fun otp(): Response<ResponseOtp>
    suspend fun authV2(username: String, password: String): Response<ResponseAuthV2>
    suspend fun profile(param: String): Response<ResponseProfile>
    suspend fun events(param: String): Response<ResponseEvents>
    suspend fun eventsRelated(param: String): Response<ResponseEventsRelated>
    suspend fun eventCategories(): Response<ResponseEventCategories>
    suspend fun researchCategories(): Response<ResponseResearchCategories>
    suspend fun getResearch(param: String): Response<ResponseResearch>
    suspend fun books(): Response<ResponseBooks>
    suspend fun webinar(): Response<ResponseWebinar>
    suspend fun codeOfConduct(): Response<ResponseCodeOfConduct>
    suspend fun checkUserLogin(param: String): Response<ResponseCheckUserLogin>
    suspend fun forgotPassword(param: RequestBody): Response<ResponsePassword>

    suspend fun auth(nt: String, p: String): Response<ResponseAuth>
    suspend fun resetPassword(p: String): Response<ResponsePassword>
    suspend fun registerStepOne(
        s: RequestBody,
        fp: MultipartBody.Part,
        nl: RequestBody,
        tl: RequestBody,
        nw: RequestBody,
        e: RequestBody,
        p: RequestBody,
        cp: RequestBody): Response<ResponseRegister>
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
        pr: RequestBody): Response<ResponseRegister>
    suspend fun getEvent(): Response<ResponseEvent>
    suspend fun getNextEvent(p: String): Response<ResponseEvent>
    suspend fun getLearningDetail(s: String): Response<ResponseLearningDetail>
    suspend fun getLearnings(k: String, c: String, y: String, m: String, ot: String): Response<ResponseLearning>
    suspend fun getNextLearnings(p: String, k: String, c: String, y: String, m: String, ot: String): Response<ResponseLearning>
    suspend fun getOnboard(): Response<ResponseOnboard>

//    suspend fun detail(s: String): Response<DrinkResponse>
//    suspend fun getRecent(): Response<DrinkResponse>
//    suspend fun getCategories(): Response<CategoryResponse>
//    suspend fun getGlasses(): Response<GlassesResponse>
//    suspend fun getIngredients(): Response<IngredientResponse>
//    suspend fun getAlcoholic(): Response<AlcoholicResponse>
//    suspend fun filter(params: Map<String, String>): Response<DrinkResponse>
}