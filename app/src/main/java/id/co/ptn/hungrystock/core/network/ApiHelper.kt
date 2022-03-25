package id.co.ptn.hungrystock.core.network

import id.co.ptn.hungrystock.models.auth.ResponseAuth
import retrofit2.Response

interface ApiHelper {
//    suspend fun getDrinkByFirstLetter(s: String): Response<DrinkResponse>
    suspend fun auth(nt: String, p: String): Response<ResponseAuth>
//    suspend fun getRandom(): Response<DrinkResponse>
//    suspend fun searchName(s: String): Response<DrinkResponse>
//    suspend fun detail(s: String): Response<DrinkResponse>
//    suspend fun getRecent(): Response<DrinkResponse>
//    suspend fun getCategories(): Response<CategoryResponse>
//    suspend fun getGlasses(): Response<GlassesResponse>
//    suspend fun getIngredients(): Response<IngredientResponse>
//    suspend fun getAlcoholic(): Response<AlcoholicResponse>
//    suspend fun filter(params: Map<String, String>): Response<DrinkResponse>
}