package id.co.ptn.hungrystock.core.network

import com.google.gson.JsonObject
import id.co.ptn.hungrystock.models.auth.ResponseAuth
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.main.home.ResponseEvent
import id.co.ptn.hungrystock.models.main.learning.ResponseLearning
import id.co.ptn.hungrystock.models.main.learning.ResponseLearningDetail
import id.co.ptn.hungrystock.models.onboard.ResponseOnboard
import id.co.ptn.hungrystock.models.password.ResponsePassword
import id.co.ptn.hungrystock.models.registration.ResponseRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Services {

    @GET(OTP)
    suspend fun otp(): Response<ResponseOtp>
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

    @GET(RESEARCH)
    suspend fun getResearch(
        @Query("type") t: String,
        @Query("keyword") k: String,
        @Query("category") c: String,
        @Query("year") y: String,
        @Query("month") m: String,
        @Query("initital") i: String): Response<JsonObject>

}