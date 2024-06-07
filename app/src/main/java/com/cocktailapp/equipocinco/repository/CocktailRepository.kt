package com.cocktailapp.equipocinco.repository

import android.content.Context
import com.cocktailapp.equipocinco.webservice.ApiService
import com.cocktailapp.equipocinco.webservice.ApiUtils
import com.cocktailapp.equipocinco.model.Drink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CocktailRepository(val context: Context) {
    private val apiService: ApiService = ApiUtils.getApiService()

    suspend fun getCocktail(cocktail: String): List<Drink> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCocktail(cocktail)
                response.drinks
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList<Drink>()
            }
        }
    }
}