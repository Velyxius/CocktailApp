package com.cocktailapp.equipocinco.webservice

import com.cocktailapp.equipocinco.model.CocktailResponse
import com.cocktailapp.equipocinco.utils.Constants.COCKTAIL_ENDPOINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(COCKTAIL_ENDPOINT)
    suspend fun getCocktail(
        @Query("s") cocktailName: String
    ): CocktailResponse
}