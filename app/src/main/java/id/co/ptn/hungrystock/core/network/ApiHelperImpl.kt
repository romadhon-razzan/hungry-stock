package id.co.ptn.hungrystock.core.network

import id.co.ptn.hungrystock.models.auth.ResponseAuth
import id.co.ptn.hungrystock.models.password.ResponsePassword
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: Services) : ApiHelper {
//    override suspend fun getDrinkByFirstLetter(s: String): Response<DrinkResponse> = apiService.getDrinkByFirstLetter(s)
    override suspend fun auth(nt: String, p: String): Response<ResponseAuth> = apiService.auth(nt, p)
    override suspend fun resetPassword(e: String): Response<ResponsePassword> = apiService.resetPassword(e)
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