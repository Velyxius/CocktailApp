package com.cocktailapp.equipocinco.view.viewholder

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.ItemListDrinkBinding
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.repository.OrderRepository
import com.cocktailapp.equipocinco.view.adapter.ListDrinkOrderAdapter
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel
import dagger.hilt.android.internal.Contexts
import java.io.Serializable

class ListDrinkOrderViewHolder(
    binding: ItemListDrinkBinding,
    navController: NavController,
    private val adapter: ListDrinkOrderAdapter):
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController



// Iterar sobre cada lista de bebidas y agregarlas al arreglo drinksArray
    data class MiObjeto(val order:Order,val position:Int):Serializable
    fun setItemListDrink(
        order: Order,
        position:Int,
        drink:MutableList<String>,
        orderViewModel: OrderViewModel
    ) {
        val quantity_drink = "${drink[0]}"
        val name_cocktail = "${drink[1]}"
        val url_coctail = "${drink[2]}"

        bindingItem.tvQuantity.text = quantity_drink
        bindingItem.valueTextView.text = name_cocktail

        val listaObjetos = listOf(
            MiObjeto(order,position)
        )
        bindingItem.cvDrinkOrder.setOnClickListener {
           val bundle = Bundle()
           bundle.putSerializable("clave",ArrayList(listaObjetos))
           navController.navigate(R.id.action_detailsOrderFragment_to_editCocktailFragment, bundle)
        }

        bindingItem.decrementButton.setOnClickListener{
            val value = drink[1].toInt() - 1
            if(value == 0){
                bindingItem.valueTextView.text = "${drink[1]}"
                order.drinks.removeAt(position)
                orderViewModel.updateOrder(order)
                adapter.removeItem(position)


            }else{

                order.drinks[position][1] = "${value}"
                bindingItem.valueTextView.text = "${drink[1]}"
                orderViewModel.updateOrder(order)

            }
        }

        bindingItem.incrementButton.setOnClickListener{
            val value = drink[1].toInt() + 1
            order.drinks[position][1] = "${value}"
            bindingItem.valueTextView.text = "${drink[1]}"
            orderViewModel.updateOrder(order)
        }

    }
}