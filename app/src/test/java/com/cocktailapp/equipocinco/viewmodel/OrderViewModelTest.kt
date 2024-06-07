package com.cocktailapp.equipocinco.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cocktailapp.equipocinco.repository.OrderRepository
import com.google.firestore.v1.StructuredQuery.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class OrderViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var orderRepository: OrderRepository
    @Before
    fun setUp() {
        orderViewModel = OrderViewModel(orderRepository)
        orderRepository = Mockito.mock(OrderRepository::class.java)
    }

    @Test
    fun  `test metodo getListOrder`() = runBlocking{
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val mockOrder = mutableListOf(
            Order()
        )
        //when

        //then


    }
}