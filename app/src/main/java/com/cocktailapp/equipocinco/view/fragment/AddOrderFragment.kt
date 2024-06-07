package com.cocktailapp.equipocinco.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.CreateOrderToolbarBinding
import com.cocktailapp.equipocinco.databinding.FragmentAddOrderBinding
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.viewmodel.CocktailViewModel
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel



class AddOrderFragment : Fragment() {
    private lateinit var binding: FragmentAddOrderBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val orderViewModel : OrderViewModel by viewModels()
    private val cocktailViewModel: CocktailViewModel by viewModels()
    private lateinit var createOrderToolbarBinding: CreateOrderToolbarBinding
    private lateinit var imageURL: String
    private lateinit var cocktailName: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddOrderBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        cocktailName = binding.etNombreC.text.toString()
        imageURL = ""
        //dataLogin()
        setupListeners()
        setupViews()
    }

    private fun setupListeners() {
        binding.fbagregarCoctel.setOnClickListener {
            guardarProducto()
        }

        binding.etNombreC.addTextChangedListener(textWatcher)

        binding.etNombreC.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                fetchImageURL()
            }
        }
    }

    private fun setupViews() {
        val drinks = resources.getStringArray(R.array.cocktails)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, drinks)
        binding.etNombreC.setAdapter(adapter)
    }
    private fun fetchImageURL() {
        cocktailViewModel.getCocktail(cocktailName)
        cocktailViewModel.cocktail.observe(viewLifecycleOwner) { drinks ->
            val drinkName = drinks.first().drinkName
            val URL = drinks.first().drinkURL
            Log.d("EstaVaGlide", drinkName)
            cocktailName = drinkName
            imageURL = URL
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            cocktailName = binding.etNombreC.text.toString()
            Log.d("NombreCoctel", cocktailName)
        }

        override fun afterTextChanged(s: Editable?) {}

    }

    private fun guardarProducto() {
        val mesa = binding.etMesNum.text.toString()
        val nombre_coctel = binding.etNombreC.text.toString()
        val cantidad = binding.etcant.text.toString()
        val url = imageURL


        if (mesa.isNotEmpty() && nombre_coctel.isNotEmpty() && cantidad.isNotEmpty()) {
            val detalleProducto: MutableList<String> = mutableListOf(nombre_coctel, cantidad, url)
            val listaProductos: MutableList<MutableList<String>> = mutableListOf(detalleProducto)
            val orden = Order(mesa, listaProductos)
            orderViewModel.saveOrder(orden)
            val bundle = Bundle()
            bundle.putSerializable("clave",orden)
            findNavController().navigate(R.id.action_addOrderFragment_to_detailsOrderFragment,bundle)
            Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

}