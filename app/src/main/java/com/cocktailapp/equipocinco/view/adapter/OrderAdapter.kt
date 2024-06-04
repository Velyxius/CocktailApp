package com.cocktailapp.equipocinco.view.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.equipocinco.databinding.ItemOrderBinding
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.view.viewholder.OrderViewHolder

class OrderAdapter(private val listOrder: MutableList<Order>,
                   private val navController: NavController
):RecyclerView.Adapter<OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return OrderViewHolder(binding,navController)
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = listOrder[position]
        holder.setItemOrder(order)
    }
}