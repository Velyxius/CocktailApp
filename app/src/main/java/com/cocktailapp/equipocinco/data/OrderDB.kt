package com.cocktailapp.equipocinco.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.utils.Constants.NAME_BD

@Database(entities = [Order::class], version = 1)
abstract class OrderDB : RoomDatabase() {

    abstract fun orderDao(): OrderDao

    companion object{
        fun getDatabase(context: Context): OrderDB {
            return Room.databaseBuilder(
                context.applicationContext,
                OrderDB::class.java,
                NAME_BD
            ).build()
        }
    }
}