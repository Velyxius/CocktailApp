package com.cocktailapp.equipocinco.repository

import com.cocktailapp.equipocinco.webservice.ApiService
import com.cocktailapp.equipocinco.model.Drink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val apiService: ApiService
) {

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