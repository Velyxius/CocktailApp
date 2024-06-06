package com.cocktailapp.equipocinco.model
import com.google.gson.annotations.SerializedName

data class CocktailResponse(
    @SerializedName("drinks") val drinks: List<Map<String, Any>>
)


g