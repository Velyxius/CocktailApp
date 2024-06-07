package com.cocktailapp.equipocinco.model

import com.google.gson.annotations.SerializedName

data class Drink(
    @SerializedName("idDrink") val drinkID: Int,
    @SerializedName("strDrink") val drinkName: String,
    @SerializedName("strDrinkThumb") val drinkURL: String
)
