package com.cocktailapp.equipocinco.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cocktailapp.equipocinco.repository.OrderRepository
import kotlinx.coroutines.launch
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.model.CocktailResponse



class OrderViewModel(application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val orderRepository = OrderRepository(context)

    private val _progressState = MutableLiveData(false)
    val progressState: LiveData<Boolean> = _progressState

    private val _listOrder = MutableLiveData<MutableList<Order>>()
    val listOrder: LiveData<MutableList<Order>> get() = _listOrder

    fun saveOrder(order: Order){
        viewModelScope.launch {
            _progressState.value = true
            try {
                orderRepository.guardarPedido(order)
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun getListOrder(){
        viewModelScope.launch {
            _progressState.value = true
            try {
                _listOrder.value = orderRepository.getListOrder()
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun updateOrder(order: Order) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                orderRepository.updateOrder(order)
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                orderRepository.eliminarPedido(order.table)
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }
}