package com.cocktailapp.equipocinco.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.ItemCocktailBinding
import com.cocktailapp.equipocinco.databinding.ItemListDrinkBinding
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.view.viewholder.ListDrinkOrderViewHolder
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel


class ListDrinkOrderAdapter (private val order: Order,
                             private val listOrder: MutableList<MutableList<String>>,
                             private val navController: NavController,
                             private val orderViewModel: OrderViewModel
):RecyclerView.Adapter<ListDrinkOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDrinkOrderViewHolder {
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ListDrinkOrderViewHolder(binding,navController,this)
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    override fun onBindViewHolder(holder: ListDrinkOrderViewHolder, position: Int) {
        val listDrink = listOrder[position]
        holder.setItemListDrink(order,position,listDrink,orderViewModel)
    }

    fun removeItem(position: Int) {
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listOrder.size)
    }
}