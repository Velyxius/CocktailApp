package com.cocktailapp.equipocinco.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String): Serializable
