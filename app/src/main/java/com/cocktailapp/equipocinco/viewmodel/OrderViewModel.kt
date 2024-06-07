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
import com.cocktailapp.equipocinco.model.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
):ViewModel() {

    //val context = getApplication<Application>()
    //private val orderRepository = OrderRepository(context)

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    private val _listOrder = MutableLiveData<MutableList<Order>>()
    val listOrder: LiveData<MutableList<Order>> get() = _listOrder

    fun saveOrder(order: Order){
        viewModelScope.launch {
            _progresState.value = true
            try {
                orderRepository.guardarPedido(order)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun getListOrder(){
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listOrder.value = orderRepository.getListOrder()
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun updateOrder(order: Order) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                orderRepository.updateOrder(order)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                orderRepository.eliminarPedido(order.table)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }
}