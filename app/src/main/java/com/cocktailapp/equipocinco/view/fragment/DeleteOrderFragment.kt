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
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.FragmentDeleteOrderBinding
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DeleteOrderFragment : Fragment() {

    private lateinit var binding: FragmentDeleteOrderBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var receivedOrder: Order
    private val orderViewModel : OrderViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteOrderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setup()
    }

    private fun setup() {
        binding.btnConfirm.setOnClickListener {
            deleteDrink()
        }

        binding.btnCancel.setOnClickListener{
            val bundle = Bundle()
            val receivedBundle = arguments
            receivedOrder = receivedBundle?.getSerializable("clave") as Order
            bundle.putSerializable("clave",receivedOrder)
            findNavController().navigate(R.id.action_deleteOrderFragment_to_detailsOrderFragment,bundle)
        }
    }

    private fun deleteDrink() {
        val receivedBundle = arguments
        receivedOrder = receivedBundle?.getSerializable("clave") as Order
        orderViewModel.deleteOrder(receivedOrder)
        findNavController().navigate(R.id.action_deleteOrderFragment_to_homeOrderFragment)
    }



}