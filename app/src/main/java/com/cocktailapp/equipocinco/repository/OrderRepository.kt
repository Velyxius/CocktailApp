package com.cocktailapp.equipocinco.repository

import android.content.Context
import com.cocktailapp.equipocinco.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class OrderRepository(val context: Context) {
    private val db = FirebaseFirestore.getInstance()

    fun eliminarPedido(table: String) {
        db.collection("order").document("1").delete()
            .addOnSuccessListener {
                println("Pedido eliminado exitosamente.")
            }
            .addOnFailureListener { e ->
                println("Error eliminando el pedido: $e")
            }
    }

    fun guardarPedido(order: Order) {
        val _order = Order(order.table, order.drinks)
        val orderData = hashMapOf(
            "table" to _order.table,
            "drinkList" to _order.drinks.map { drinkList ->
                hashMapOf("drink" to drinkList)
            }
        )

        db.collection("order").document(_order.table).set(orderData)
            .addOnSuccessListener {
                println("Pedido guardado correctamente")
            }
            .addOnFailureListener { e ->
                println("Error guardando el pedido: $e")
            }
    }

    fun updateOrder(order: Order) {
        val _order = Order(order.table, order.drinks)
        val orderData = hashMapOf(
            "table" to _order.table,
            "drinkList" to _order.drinks.map { drinkList ->
                hashMapOf("drink" to drinkList)
            }
        )

        val docRef = db.collection("order").document(_order.table)
        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                docRef.update(orderData as Map<String, Any>)
                    .addOnSuccessListener {
                        println("Pedido actualizado correctamente")
                    }
                    .addOnFailureListener { e ->
                        println("Error actualizando el pedido: $e")
                    }
            } else {
                println("No se encontró el pedido para actualizar")
            }
        }.addOnFailureListener { e ->
            println("Error buscando el pedido: $e")
        }
    }

    suspend fun getListOrder(): MutableList<Order>? {
        return try {
            val querySnapshot = db.collection("order").get().await()
            val pedidos: MutableList<Order> = mutableListOf()
            for (document in querySnapshot.documents) {
                val bebidas = (document.get("drinkList") as? List<Map<String, List<String>>>)?.map { it["drink"] ?: emptyList() } ?: emptyList()
                val bebidasListasMutable = bebidas.map { it?.toMutableList() ?: mutableListOf() }.toMutableList()
                pedidos.add(Order(document.getString("table") ?: "", bebidasListasMutable))
            }
            pedidos
        } catch (e: Exception) {
            null
        }
    }

}