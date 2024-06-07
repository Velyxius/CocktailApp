package com.cocktailapp.equipocinco.model

import com.google.gson.annotations.SerializedName
import com.cocktailapp.equipocinco.model.Drink

data class CocktailResponse(
    @SerializedName("drinks") val drinks: List<Drink>
)