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
import android.widget.TextView
import androidx.databinding.DataBindingUtil.setContentView
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.FragmentEditCocktailBinding
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.view.viewholder.ListDrinkOrderViewHolder
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel
import com.google.firebase.auth.FirebaseAuth


class EditCocktailFragment : Fragment() {
    private lateinit var binding: FragmentEditCocktailBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val orderViewModel : OrderViewModel by viewModels()
    private lateinit var receivedOrder: Order
    private var receivedPosition = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditCocktailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)

        setup()
        setOrder()

    }

    private fun setup() {
        binding.fbeditarCoctel.setOnClickListener {
            updateOrder()
        }
    }
    private fun setOrder() {
        val receivedBundle = arguments
        val listaRecuperada = receivedBundle?.getSerializable("clave") as ArrayList<ListDrinkOrderViewHolder.MiObjeto>?
        //val drinkName =
        listaRecuperada?.forEach { miObjeto ->
            val order = miObjeto.order
            receivedOrder = order
            receivedPosition = miObjeto.position
            val drink = order.drinks[miObjeto.position][0]
            val numberDrink = order.drinks[miObjeto.position][1]
            binding.etNombreC.setText(drink)
            binding.etcantidad.setText(numberDrink)
        }
    }

    private fun updateOrder(){
        val nombre_coctel = binding.etNombreC.text.toString()
        val cantidad_coctel = binding.etcantidad.text.toString()
        val url = ""
        val modifieDrink: MutableList<String> = mutableListOf(nombre_coctel,cantidad_coctel,url)
        val listDrinks: MutableList<MutableList<String>> = receivedOrder.drinks
        listDrinks[receivedPosition] = modifieDrink
        if (nombre_coctel.isNotEmpty() && cantidad_coctel.isNotEmpty()) {
            val orden = Order(receivedOrder.table, receivedOrder.drinks)
            orderViewModel.updateOrder(orden)
            val bundle = Bundle()
            bundle.putSerializable("clave",orden)
            findNavController().navigate(R.id.action_editCocktailFragment_to_detailsOrderFragment,bundle)
            Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        binding.etNombreC.setText("")
        binding.etcantidad.setText("")
    }
}