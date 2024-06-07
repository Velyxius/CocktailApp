package com.cocktailapp.equipocinco.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.FragmentHomeOrderBinding
import com.cocktailapp.equipocinco.view.LoginActivity
import com.cocktailapp.equipocinco.view.MainActivity
import com.cocktailapp.equipocinco.view.adapter.OrderAdapter
import com.cocktailapp.equipocinco.viewmodel.OrderViewModel
import com.cocktailapp.equipocinco.databinding.ItemOrderBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeOrderFragment : Fragment() {

    private lateinit var binding: FragmentHomeOrderBinding
    private lateinit var bindingItem: ItemOrderBinding
    private val orderViewModel: OrderViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeOrderBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        loginData()
        controladores()
        observadorViewModel()
    }

    private fun controladores() {
        binding.fbagregar.setOnClickListener {
            findNavController().navigate(R.id.action_homeOrderFragment_to_addOrderFragment)
        }
        binding.logoutBtn.setOnClickListener {
            logOut()
        }

    }

    private fun observadorViewModel(){
        observerListOrder()
        observerProgress()

    }

    private fun observerListOrder(){

        orderViewModel.getListOrder()
        orderViewModel.listOrder.observe(viewLifecycleOwner){ listOrder ->
            val recycler = binding.recyclerview
            val layoutManager =LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = OrderAdapter(listOrder, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()

        }

    }

    private fun observerProgress(){
        orderViewModel.progressState.observe(viewLifecycleOwner){status ->
            binding.progress.isVisible = status
        }
    }

    private fun loginData() {
        val bundle = requireActivity().intent.extras
        val email = bundle?.getString("email")
        sharedPreferences.edit().putString("email",email).apply()
    }

    private fun logOut(){
        sharedPreferences.edit().clear().apply()
        FirebaseAuth.getInstance().signOut()
        (requireActivity() as MainActivity).apply {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }



}