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
import com.cocktailapp.equipocinco.databinding.FragmentAddOrderBinding
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel



class AddOrderFragment : Fragment() {
    private lateinit var binding: FragmentAddOrderBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val orderViewModel : OrderViewModel by viewModels()

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
        //dataLogin()
        setup()

    }

    private fun setup() {

        binding.btnGuardarArticulo.setOnClickListener {
            guardarProducto()
        }
    }
    private fun guardarProducto() {
        val codigo = binding.etCodigo.text.toString()
        val nombreArticulo = binding.etNombreArticulo.text.toString()
        val precio = binding.etPrecio.text.toString()

        if (codigo.isNotEmpty() && nombreArticulo.isNotEmpty() && precio.isNotEmpty()) {
            val detalleProducto: MutableList<String> = mutableListOf(nombreArticulo, precio)
            val listaProductos: MutableList<MutableList<String>> = mutableListOf(detalleProducto)
            val orden = Order(codigo, listaProductos)
            orderViewModel.saveOrder(orden)
            val bundle = Bundle()
            bundle.putSerializable("clave",orden)
            findNavController().navigate(R.id.action_addOrderFragment_to_detailsOrderFragment,bundle)
            Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }




    private fun limpiarCampos() {
        binding.etCodigo.setText("")
        binding.etNombreArticulo.setText("")
        binding.etPrecio.setText("")
    }

}