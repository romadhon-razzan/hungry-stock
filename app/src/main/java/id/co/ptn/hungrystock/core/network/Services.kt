package id.co.ptn.hungrystock.core.network

import com.google.gson.JsonObject
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
import id.co.ptn.hungrystock.models.reference.ResponseResearchCategoriesData
import id.co.ptn.hungrystock.models.registration.ResponseRegister
import id.co.ptn.hungrystock.models.user.ResponseProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Services {

    @GET(OTP)
    suspend fun otp(): Response<ResponseOtp>
    @FormUrlEncoded
    @POST(CUSTOMER_LOGIN)
    suspend fun authV2(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("deviceid") deviceid: String,
    ): Response<ResponseAuthV2>

    @GET("${PROFILE}/{param}")
    suspend fun profile(
        @Path("param") param: String,
    ): Response<ResponseProfile>

    @GET("${EVENTS}/{param}")
    suspend fun events(
        @Path("param") param: String,
    ): Response<ResponseEvents>
    @GET("${EVENT_RELATED}/{param}")
    suspend fun eventsRelated(
        @Path("param") param: String,
    ): Response<ResponseEventsRelated>

    @GET(EVENT_CATEGORIES)
    suspend fun eventCategories(): Response<ResponseEventCategories>

    @GET(RESEARCH_CATEGORIES)
    suspend fun researchCategories(): Response<ResponseResearchCategories>

    @GET(BOOKS)
    suspend fun books(): Response<ResponseBooks>
    @GET(WEBINAR)
    suspend fun webinar(): Response<ResponseWebinar>

    @GET(CODE_OF_CONDUCT)
    suspend fun codeOfConduct(): Response<ResponseCodeOfConduct>
    @GET("${CHECK_USER_LOGIN}/{param}")
    suspend fun checkUserLogin(
        @Path("param") param: String,
    ): Response<ResponseCheckUserLogin>
    @POST(FORGOT_PASSWORD)
    suspend fun forgotPassword(@Body requestBody: RequestBody): Response<ResponsePassword>



    @FormUrlEncoded
    @POST(AUTH)
    suspend fun auth(
    @Field("nomor_telepon") nomor_telepon: String,
    @Field("password") password: String,
    ): Response<ResponseAuth>

    @FormUrlEncoded
    @POST(RESET_PASSWORD)
    suspend fun resetPassword(
        @Field("email") email: String,
    ): Response<ResponsePassword>

    @Multipart
    @POST(REGISTER)
    suspend fun registerStepOne(
        @Part("step") step: RequestBody,
        @Part foto_profil: MultipartBody.Part,
        @Part("nama_lengkap") nama_lengkap: RequestBody,
        @Part("tanggal_lahir") tanggal_lahir: RequestBody,
        @Part("nomor_whatsapp") nomor_whatsapp: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("password_confirmation") password_confirmation: RequestBody
    ): Response<ResponseRegister>

    @Multipart
    @POST(REGISTER)
    suspend fun registerStepTwo(
        @Part("step") step: RequestBody,
        @Part foto_profil: MultipartBody.Part,
        @Part bukti_bayar:  MultipartBody.Part,
        @Part("nama_lengkap") nama_lengkap: RequestBody,
        @Part("nomor_whatsapp") nomor_whatsapp: RequestBody,
        @Part("email") email: RequestBody,
        @Part("tanggal_lahir") tanggal_lahir: RequestBody,
        @Part("password") password: RequestBody,
        @Part("domisili") domisili: RequestBody,
        @Part("lama_investasi") lama_investasi: RequestBody,
        @Part("profesi") profesi: RequestBody,
        @Part("pendidikan") pendidikan: RequestBody,
        @Part("portofolio") portofolio: RequestBody
    ): Response<ResponseRegister>

    @GET(EVENT)
    suspend fun getEvent(): Response<ResponseEvent>

    @GET(EVENT)
    suspend fun getNextEvent(@Query("page") p: String): Response<ResponseEvent>

    @GET("${LEARNING}/{suffix}")
    suspend fun getLearningDetail(@Path(value = "suffix", encoded = true) suffix: String): Response<ResponseLearningDetail>

    @GET(LEARNING)
    suspend fun getLearnings(
        @Query("keyword") k: String,
        @Query("category") c: String,
        @Query("year") y: String,
        @Query("month") m: String,
        @Query("order_type") ot: String): Response<ResponseLearning>

    @GET(LEARNING)
    suspend fun getNextLearnings(
        @Query("page") p: String,
        @Query("keyword") k: String,
        @Query("category") c: String,
        @Query("year") y: String,
        @Query("month") m: String,
        @Query("order_type") ot: String): Response<ResponseLearning>

    @GET(HOME)
    suspend fun getOnboard(): Response<ResponseOnboard>

    @GET("$RESEARCH/{param}")
    suspend fun getResearch(
        @Path("param") param: String): Response<ResponseResearch>

}