package com.cocktailapp.equipocinco.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cocktailapp.equipocinco.R
import com.cocktailapp.equipocinco.databinding.ActivityLoginBinding
import com.cocktailapp.equipocinco.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    }
    private fun setup() {

    }

    private fun saveSession(email: String) {

    }
    private fun registerUser(){


    }

    private fun goToHome(){
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun loginUser(){

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordVisibilityToggle() {

    }

    private fun validatePassword(password: String) {

    }



    private fun sesion(){

    }



    private fun checkSession() {

    }

    private fun createTextWatcher(): TextWatcher {

    }


    override fun onStart() {

    }
}