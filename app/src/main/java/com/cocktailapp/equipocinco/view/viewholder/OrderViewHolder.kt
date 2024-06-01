package com.cocktailapp.equipocinco.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.ItemOrderBinding
import com.cocktailapp.equipocinco.model.Order
import java.text.NumberFormat
import java.util.Locale
import java.util.*

class OrderViewHolder (binding: ItemOrderBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController
    fun setItemOrder(order: Order) {


        bindingItem.cvOrder.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", order)
            navController.navigate(R.id.action_homeOrderFragment_to_detailsOrderFragment, bundle)
        }

    }
}