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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.FragmentAddCocktailBinding
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.viewmodel.CocktailViewModel
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCocktailFragment : Fragment() {
    private lateinit var binding: FragmentAddCocktailBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val orderViewModel : OrderViewModel by viewModels()
    private val cocktailViewModel: CocktailViewModel by viewModels()
    private lateinit var receivedOrder: Order
    private lateinit var imageURL: String
    private lateinit var cocktailName: String

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
        cocktailName = binding.etNombreC.text.toString()
        imageURL = ""
        setup()
        setupViews()
    }

    private fun setup() {
        binding.fbCancelCoctel.setOnClickListener {
            val receivedBundle = arguments
            receivedOrder = receivedBundle?.getSerializable("clave")  as Order
            val bundle = Bundle()
            bundle.putSerializable("clave",receivedOrder)
            findNavController().navigate(R.id.action_addCocktailFragment_to_detailsOrderFragment,bundle)
        }
        
        binding.fbagregarCoctel.setOnClickListener {
            updateOrder()
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
            // Log.d("EstaVaGlide", drinkName)
            cocktailName = drinkName
            imageURL = URL
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            cocktailName = binding.etNombreC.text.toString()
            // Log.d("NombreCoctel", cocktailName)
        }

        override fun afterTextChanged(s: Editable?) {}

    }

    private fun updateOrder(){
        val receivedBundle = arguments
        receivedOrder = receivedBundle?.getSerializable("clave") as Order
        val drinkName = binding.etNombreC.text.toString()
        val quantity = binding.etcant.text.toString()
        val url = imageURL
        val additionalDrink: MutableList<String> = mutableListOf(drinkName, quantity, url)

        if (drinkName.isNotEmpty() && quantity.isNotEmpty()) {
            receivedOrder.drinks.add(additionalDrink)
            val order = Order(receivedOrder.table, receivedOrder.drinks)
            orderViewModel.updateOrder(order)
            Toast.makeText(context, "Cóctel agregado con éxito", Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            bundle.putSerializable("clave", receivedOrder)
            findNavController().navigate(R.id.action_addCocktailFragment_to_detailsOrderFragment,bundle)
        } else {
            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

}