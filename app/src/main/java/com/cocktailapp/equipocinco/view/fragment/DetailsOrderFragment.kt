package com.cocktailapp.equipocinco.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.view.adapter.ListDrinkOrderAdapter
import com.cocktailapp.equipocinco.databinding.FragmentDetailsOrderBinding
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel


class DetailsOrderFragment : Fragment() {
    private lateinit var binding: FragmentDetailsOrderBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var receivedOrder: Order


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsOrderBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        dataListDrink()
        addDrink()
        deleteDrink()
    }

    private fun dataListDrink() {
        val receivedBundle = arguments
        val orderViewModel: OrderViewModel by viewModels()
        receivedOrder = receivedBundle?.getSerializable("clave")  as Order
        val receiveDrinks: MutableList<MutableList<String>> = receivedOrder.drinks
        val recycler = binding.recyclerview
        val layoutManager = LinearLayoutManager(context)
        binding.tbDetailsOrder.toolbarTitle.text = "Mesa ${receivedOrder.table}"
        recycler.layoutManager = layoutManager
        val adapter = ListDrinkOrderAdapter(
            receivedOrder,
            receiveDrinks,
            findNavController(),
            orderViewModel
        )
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun addDrink(){
        binding.fbagregar.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave",receivedOrder)
            findNavController().navigate(R.id.action_detailsOrderFragment_to_addCocktailFragment,bundle)
        }
    }

    private fun deleteDrink(){
        binding.trashAllBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave",receivedOrder)
            findNavController().navigate(R.id.action_detailsOrderFragment_to_deleteOrderFragment,bundle)
        }
    }


}