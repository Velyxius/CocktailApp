package com.cocktailapp.equipocinco.repository
import com.cocktailapp.equipocinco.webservice.ApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject




class LoginRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
){
    //private val firebaseAuth = FirebaseAuth.getInstance()
    fun registerUser(email: String, pass:String, isRegisterComplete: (Boolean)->Unit){
        if(email.isNotEmpty() && pass.isNotEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isRegisterComplete(true)

                    } else {
                        isRegisterComplete(false)
                    }
                }
        }else{
            isRegisterComplete(false)
        }
    }

    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLogin(true)
                    } else {
                        isLogin(false)
                    }
                }
        } else {
            isLogin(false)
        }
    }
}
