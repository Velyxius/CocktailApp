package com.cocktailapp.equipocinco.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.equipocinco.databinding.ItemOrderBinding
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.model.Order


class OrderViewHolder(binding: ItemOrderBinding, navController: NavController):
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController


// Iterar sobre cada lista de bebidas y agregarlas al arreglo drinksArray

    fun setItemOrder(order: Order) {

        bindingItem.tvPetName.text = order.table
        println("NUMBER TABLE")
        println(order.table)
        //bindingItem.tvPrice.text = "$ ${order.drinks[0][0]} "
        bindingItem.cvOrder.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", order)
            navController.navigate(R.id.action_homeOrderFragment_to_detailsOrderFragment, bundle)
        }

    }
}