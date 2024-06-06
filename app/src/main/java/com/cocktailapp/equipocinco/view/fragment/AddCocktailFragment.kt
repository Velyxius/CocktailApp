package com.cocktailapp.equipocinco.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.FragmentAddCocktailBinding
import com.cocktailapp.equipocinco.viewmodel.CocktailViewModel
class AddCocktailFragment : Fragment() {
    private lateinit var binding: FragmentAddCocktailBinding
    private val cocktailViewModel: CocktailViewModel by viewModels()
    private lateinit var imageUrl: String

    private val drinks = resources.getStringArray(R.array.cocktails)

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (isAdded) {
            Log.d("Contextual", "Added to Context")
        }

        binding = FragmentAddCocktailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupViews()
        setupListeners()
        observerViewModel()
    }

    private fun setupAdapter() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, drinks)
        binding.etNombreC.setAdapter(adapter)
    }

    private fun setupViews() {
        // TODO
    }

    private fun setupListeners()
    {
        // TODO
    }

    private fun observerViewModel() {
        // TODO
    }
}