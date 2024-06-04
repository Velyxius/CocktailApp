package com.cocktailapp.equipocinco.model


import java.io.Serializable


data class Order(
    val table: String,
    val drinks: MutableList<MutableList<String>> = mutableListOf()
):Serializable
