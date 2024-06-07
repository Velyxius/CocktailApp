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
        binding.btnGuardarArticulo.setOnClickListener {
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
            binding.etNombreArticulo.setText(drink)
            binding.etPrecio.setText(numberDrink)
        }
    }

    private fun updateOrder(){
        val nombreArticulo = binding.etNombreArticulo.text.toString()
        val precio = binding.etPrecio.text.toString()
        val url = ""
        val modifieDrink: MutableList<String> = mutableListOf(nombreArticulo,precio,url)
        val listDrinks: MutableList<MutableList<String>> = receivedOrder.drinks
        listDrinks[receivedPosition] = modifieDrink
        if (nombreArticulo.isNotEmpty() && precio.isNotEmpty()) {
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
        binding.etNombreArticulo.setText("")
        binding.etPrecio.setText("")
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditCocktailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditCocktailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



}