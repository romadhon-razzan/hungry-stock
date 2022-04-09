package id.co.ptn.hungrystock.core.network

import id.co.ptn.hungrystock.models.auth.ResponseAuth
import id.co.ptn.hungrystock.models.main.home.ResponseEvent
import id.co.ptn.hungrystock.models.password.ResponsePassword
import id.co.ptn.hungrystock.models.registration.ResponseRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Services {
//    @GET(GET_DRINK_BY_FIRST_LETTER)
//    suspend fun getDrinkByFirstLetter(@Query("f") s: String): Response<DrinkResponse>
//

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
//
//    @GET(SEARCH_NAME)
//    suspend fun searchName( @Query("s") s: String): Response<DrinkResponse>
//
//    @GET(GET_DETAIL)
//    suspend fun detail( @Query("i") s: String): Response<DrinkResponse>
//
//    @GET(GET_RECENT)
//    suspend fun getRecent(): Response<DrinkResponse>
//
//    @GET(GET_CATEGORIES)
//    suspend fun getCategories(): Response<CategoryResponse>
//
//    @GET(GET_GLASSES)
//    suspend fun getGlasses(): Response<GlassesResponse>
//
//    @GET(GET_INGREDIENTS)
//    suspend fun getIngredients(): Response<IngredientResponse>
//
//    @GET(GET_ALCOHOLIC)
//    suspend fun getAlcoholic(): Response<AlcoholicResponse>
//
//    @GET(FILTER)
//    suspend fun filter(@QueryMap params: Map<String, String>): Response<DrinkResponse>
}