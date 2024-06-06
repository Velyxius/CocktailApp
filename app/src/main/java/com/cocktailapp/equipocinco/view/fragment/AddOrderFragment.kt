package com.cocktailapp.equipocinco.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.FragmentAddOrderBinding
import com.cocktailapp.equipocinco.view.adapter.OrderAdapter
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel

class AddOrderFragment : Fragment() {
    private lateinit var binding: FragmentAddOrderBinding
    private val orderViewModel: OrderViewModel by viewModels()

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
        controladores()
        observerViewModel()
        recycler()
    }

    private fun observerViewModel() {
        observerListOrder()
    }


    private fun observerListOrder() {
        orderViewModel.getListOrder()

        orderViewModel.listOrder.observe(viewLifecycleOwner) {listOrder ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = OrderAdapter(listOrder, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun controladores() {
        binding.fbagregar.setOnClickListener {
            findNavController().navigate(R.id.action_addOrderFragment_to_addCocktailFragment)
        }
    }

    private fun recycler(){
        val recycler = binding.recyclerview
        recycler.layoutManager = LinearLayoutManager(context)
    }
}