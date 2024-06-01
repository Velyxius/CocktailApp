package com.cocktailapp.equipocinco.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cocktailapp.equipocinco.model.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrder(order: Order)

    @Query("SELECT * FROM \"Order\"")
    suspend fun getListOrder(): MutableList<Order>

    @Delete
    suspend fun deleteOrder(order: Order)
    @Update
    suspend fun updateOrder(order: Order)

}