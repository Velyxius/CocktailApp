package com.cocktailapp.equipocinco.webservice

import com.cocktailapp.equipocinco.model.Cocktail
import com.cocktailapp.equipocinco.utils.Constants.END_POINT_x
import retrofit2.http.GET

interface ApiService {
    @GET(END_POINT_x)
    suspend fun getCocktail(): MutableList<Cocktail>
}