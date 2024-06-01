package com.cocktailapp.equipocinco.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cocktailapp.equipocinco.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



/*
class LoginViewModel : ViewModel() {
    private var repository = LoginRepository()
    */
@HiltViewModel
class LoginViewModel @Inject constructor (
    private val repository: LoginRepository,
    private var firebaseAuth: FirebaseAuth
) : ViewModel() {

}