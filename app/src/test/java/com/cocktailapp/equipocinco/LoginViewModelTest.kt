package com.cocktailapp.equipocinco

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cocktailapp.equipocinco.repository.LoginRepository
import com.cocktailapp.equipocinco.viewmodel.LoginViewModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever


class LoginViewModelTest {

    @Mock
    lateinit var firebaseAuth: FirebaseAuth

    @get:Rule
    val rule = InstantTaskExecutorRule() //código que involucra LiveData y ViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginRepository: LoginRepository
    @Captor
    private lateinit var isRegisterCaptor: ArgumentCaptor<(Boolean) -> Unit>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loginRepository = mock(LoginRepository::class.java)
        loginViewModel = LoginViewModel(loginRepository,firebaseAuth)
        println("LoginRepository instance in test: $loginRepository")
        println("LoginRepository instance in ViewModel: ${loginViewModel.getRepository()}")
    }

    @Test
    fun testRegisterUser() {
        val email = "test@example.com"
        val pass = "123456"

        // Configuración del comportamiento simulado del repository
        Mockito.`when`(loginRepository.registerUser(eq(email), eq(pass), any()))
            .thenAnswer { invocation ->
                val callback = invocation.getArgument<(Boolean) -> Unit>(2)
                // Simular que la respuesta es exitosa
                callback.invoke(true)
            }

        // Llamada al método que estás probando
        loginViewModel.registerUser(email, pass) { isRegister ->
            // Verificación de resultados usando assertions
            assertTrue(isRegister)
        }
    }

    @Test
    fun testLoginUser() {
        val email = "marlet@gmail.com"
        val pass = "555555"

        // Configuración del comportamiento simulado de FirebaseAuth
        val successfulTask = Tasks.forResult<AuthResult>(Mockito.mock(AuthResult::class.java))
        val failedTask = Tasks.forException<AuthResult>(Exception("Simulated Failure"))

        val mockFirebaseAuth = mock(FirebaseAuth::class.java)
        whenever(mockFirebaseAuth.signInWithEmailAndPassword(email, pass)).thenReturn(successfulTask)
        whenever(mockFirebaseAuth.signInWithEmailAndPassword("", "")).thenReturn(failedTask)

        // Prueben descomentando, también funciona
/*        // Llamada al método que estás probando
            loginViewModel.loginUser(email, pass) { isLogin ->
            // Verificación de resultados usando assertions
            assertTrue(isLogin)
        }*/
        // Llamada al método que estás probando con valores vacíos
        loginViewModel.loginUser("", "") { isLogin ->
            // Verificación de resultados usando assertions
            assertFalse(isLogin)
        }
    }

    @Test
    fun testRegisterUserWithValidCredentials() {
        val email = "test_2@example.com"
        val password = "password123"

        loginViewModel.registerUser(email, password) { isRegister ->
            assertTrue(isRegister)
        }
    }

    @Test
    fun testInactiveSession() {
        val email: String? = null

        loginViewModel.sesion(email) { isEnableView ->
            assertFalse(isEnableView)
        }
    }

    @Test
    fun testRegisterUserFailure() {
        val email = "test@example.com"
        val password = "password123"

        Mockito.`when`(loginRepository.registerUser(eq(email), eq(password), any()))
            .thenAnswer { invocation ->
                val callback = invocation.getArgument<(Boolean) -> Unit>(2)
                // Simular respuesta es fallida
                callback.invoke(false)
            }

        loginViewModel.registerUser(email, password) { isRegister ->
            assertFalse(isRegister)
        }
    }

    // TODO: Daniel revisa porfa
    @Test
    fun logoutUser_Success() {
        // Mock successful sign-out
        Mockito.`when`(firebaseAuth.signOut()).then { /* simulate successful sign-out */ }

        // Perform the logout operation
        loginViewModel.logoutUser { success, error ->
            // Verify that the callback indicates success and no error message
            assert(success)
            assert(error == null)
        }
    }
}



