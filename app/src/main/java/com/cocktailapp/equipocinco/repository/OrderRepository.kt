package com.cocktailapp.equipocinco.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.cocktailapp.equipocinco.data.OrderDao
import javax.inject.Inject
import com.cocktailapp.equipocinco.model.Cocktail
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.webservice.ApiService
import kotlinx.coroutines.tasks.await

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
    private val apiService: ApiService,
    private val db: FirebaseFirestore,
) {

    suspend fun saveOrder(order: Order) {
        withContext(Dispatchers.IO) {
            db.collection("oredr").document(order.id.toString()).set(
                hashMapOf(
                    "id" to order.id,
                    "name" to order.name
                )
            )
        }
    }

    suspend fun getListOrder():MutableList<Order>{
        return withContext(Dispatchers.IO){
            try {
                val snapshot = db.collection("order").get().await()
                val oderList = mutableListOf<Order>()
                for (document in snapshot.documents) {
                    val id = document.getLong("id")?.toInt() ?: 0
                    val name = document.getString("name") ?: ""

                    val item = Order(id, name)
                    oderList.add(item)
                }

                oderList
            } catch (e: Exception) {
                e.printStackTrace()
                mutableListOf()
            }
        }
    }

    suspend fun deleteOrder(order: Order){
        withContext(Dispatchers.IO) {
            // Eliminar el documento en Firebase Firestore
            db.collection("order").document(order.id.toString()).delete()

            // podria implementarse a futuro los precios y acá la cuenta total
            // calculateTotalEarnings()
            // También puedes eliminar el objeto localmente si es necesario
            // inventoryDao.deleteInventory(inventory)
        }
    }

    suspend fun updateOrderRepository(order: Order) {
        withContext(Dispatchers.IO) {
            db.collection("order").document(order.id.toString()).set(
                hashMapOf(
                    "id" to order.id,
                    "name" to order.name,
                )
            )

            // podria implementarse a futuro los precios y acá la cuenta total
            // calculateTotalEarnings()
        }
    }
}