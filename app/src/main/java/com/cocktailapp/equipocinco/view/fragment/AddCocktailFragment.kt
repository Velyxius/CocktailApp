package com.cocktailapp.equipocinco.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.FragmentAddCocktailBinding
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel


class AddCocktailFragment : Fragment() {
    private lateinit var binding: FragmentAddCocktailBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val orderViewModel : OrderViewModel by viewModels()
    private lateinit var receivedOrder: Order
    private var receivedPosition = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCocktailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setup()
    }

    private fun setup() {
        binding.btnGuardarArticulo.setOnClickListener {
            updateOrder()
        }
    }

    private fun updateOrder(){
        val receivedBundle = arguments
        receivedOrder = receivedBundle?.getSerializable("clave")  as Order
        val nombreArticulo = binding.etNombreArticulo.text.toString()
        val precio = binding.etPrecio.text.toString()
        val aditionalDrink: MutableList<String> = mutableListOf(nombreArticulo,precio)

        if (nombreArticulo.isNotEmpty() && precio.isNotEmpty()) {
            receivedOrder.drinks.add(aditionalDrink)
            val orden = Order(receivedOrder.table, receivedOrder.drinks)
            orderViewModel.updateOrder(orden)
            Toast.makeText(context, "Coctel agregado con exito", Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            bundle.putSerializable("clave",receivedOrder)
            findNavController().navigate(R.id.action_addCocktailFragment_to_detailsOrderFragment,bundle)
        } else {
            Toast.makeText(context, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

}