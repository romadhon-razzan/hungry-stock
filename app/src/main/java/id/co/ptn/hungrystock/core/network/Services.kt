package id.co.ptn.hungrystock.core.network

import id.co.ptn.hungrystock.models.auth.ResponseAuth
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
//
//    @GET(GET_RANDOM)
//    suspend fun getRandom(): Response<DrinkResponse>
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