package com.cocktailapp.equipocinco.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cocktailapp.equipocinco.model.Order
import com.cocktailapp.equipocinco.repository.OrderRepository
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
        orderRepository = Mockito.mock(OrderRepository::class.java)
        orderViewModel = OrderViewModel(orderRepository)

    }

    @Test
    fun  `test metodo getListOrder`() = runBlocking{
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val drink1: MutableList<String> = mutableListOf("Margarita", "2"," https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg")
        val list_drink1: MutableList<MutableList<String>> = mutableListOf(drink1)
        val drink2: MutableList<String> = mutableListOf("Gimlet", "3", "https://www.thecocktaildb.com/images/media/drink/3xgldt1513707271.jpg")
        val list_drink2: MutableList<MutableList<String>> = mutableListOf(drink2)
        val mockOrder = mutableListOf(
            Order("1", list_drink1),
            Order("2", list_drink2)
        )
        Mockito.`when`(orderRepository.getListOrder()).thenReturn(mockOrder)
        //when
        orderViewModel.getListOrder()
        println(mockOrder)
        println(orderViewModel.listOrder.value)
        //then
        assertEquals(orderViewModel.listOrder.value,mockOrder)

    }

    @Test
    fun `test saveOrder`() = runBlocking {
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val drink2: MutableList<String> = mutableListOf("Gimlet", "3", "https://www.thecocktaildb.com/images/media/drink/3xgldt1513707271.jpg")
        val list_drink2: MutableList<MutableList<String>> = mutableListOf(drink2)
        val order = Order("3",list_drink2)
        //when
        orderViewModel.saveOrder(order)
        //then
        Mockito.verify(orderRepository).guardarPedido(order)

    }

    @Test
    fun `test updateOrder`() = runBlocking {
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val drink1: MutableList<String> = mutableListOf("Margarita", "2"," https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg")
        val list_drink2: MutableList<MutableList<String>> = mutableListOf(drink1)
        val order = Order("2",list_drink2)
        //when
        orderViewModel.updateOrder(order)
        //then
        Mockito.verify(orderRepository).updateOrder(order)

    }

    @Test
    fun `test deleteOrder`() = runBlocking {
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val drink1: MutableList<String> = mutableListOf("Margarita", "2"," https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg")
        val list_drink2: MutableList<MutableList<String>> = mutableListOf(drink1)
        val order = Order("2",list_drink2)
        //when
        orderViewModel.deleteOrder(order)
        //then
        Mockito.verify(orderRepository).eliminarPedido(order.table)

    }
}