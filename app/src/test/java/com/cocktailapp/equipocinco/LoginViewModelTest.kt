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
    val rule = InstantTaskExecutorRule()
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
        val password = "123456"

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

    @Test
    fun logoutUser_Success() {
        // Mock successful sign-out
        Mockito.`when`(firebaseAuth.signOut()).then { }

        loginViewModel.logoutUser { success, error ->
            assert(success)
            assert(error == null)
        }
    }

    @Test
    fun testActiveSession() {
        val email = "active_user@example.com"

        loginViewModel.sesion(email) { isEnableView ->
            assertTrue(isEnableView)
        }
    }

    @Test
    fun testLoginUserWithIncorrectPassword() {
        val email = "test@example.com"
        val password = "999999" // Contraseña incorrecta

        // Simular comportamiento del repository para contraseña incorrecta
        Mockito.`when`(loginRepository.loginUser(eq(email), eq(password), any()))
            .thenAnswer { invocation ->
                val callback = invocation.getArgument<(Boolean) -> Unit>(2)
                // Simular respuesta es fallida
                callback.invoke(false)
            }

        loginViewModel.loginUser(email, password) { isLogin ->
            assertFalse(isLogin)
        }
    }

    @Test
    fun testLoginUserWithInvalidCredentials() {
        val email = "invalid_user@example.com"
        val password = "111111"

        // Simular comportamiento del repository para credenciales inválidas
        Mockito.`when`(loginRepository.loginUser(eq(email), eq(password), any()))
            .thenAnswer { invocation ->
                val callback = invocation.getArgument<(Boolean) -> Unit>(2)
                // Simular respuesta es fallida
                callback.invoke(false)
            }

        loginViewModel.loginUser(email, password) { isLogin ->
            assertFalse(isLogin)
        }
    }

    @Test
    fun logoutUser_Failure() {
        // Simular comportamiento del repository para un cierre de sesión fallido
        Mockito.`when`(firebaseAuth.signOut()).thenThrow(RuntimeException("Simulated sign-out error"))

        // Realizar la operación de cierre de sesión
        loginViewModel.logoutUser { success, error ->
            // Verifica que la devolución de llamada indique un error y contenga un mensaje de error
            assertFalse(success)
            assertNotNull(error)
        }
    }

    /*
        Verifica que el registro falla cuando se proporciona un correo electrónico
        con un formato inválido.
     */
    @Test
    fun testRegisterUserWithInvalidEmail() {
        val email = "invalid-email"
        val password = "password123"

        loginViewModel.registerUser(email, password) { isRegister ->
            assertFalse(isRegister)
        }
    }

    /**
     * Verifica que el registro falla cuando se proporciona una contraseña que
     * no cumple con los requisitos mínimos de seguridad (por ejemplo, menos de 6 caracteres).
     */
    @Test
    fun testRegisterUserWithWeakPassword() {
        val email = "test@example.com"
        val password = "123"

        loginViewModel.registerUser(email, password) { isRegister ->
            assertFalse(isRegister)
        }
    }

    /**
     * Verifica que el inicio de sesión falla
     * cuando se utiliza un correo electrónico que no está registrado.
     */
    @Test
    fun testLoginUserWithUnregisteredEmail() {
        val email = "unregistered@example.com"
        val password = "123456"

        // Simular comportamiento del repository para un correo no registrado
        Mockito.`when`(loginRepository.loginUser(eq(email), eq(password), any()))
            .thenAnswer { invocation ->
                val callback = invocation.getArgument<(Boolean) -> Unit>(2)
                // Simular respuesta es fallida
                callback.invoke(false)
            }

        loginViewModel.loginUser(email, password) { isLogin ->
            assertFalse(isLogin)
        }
    }


    @Test
    fun testRegisterUserWithExistingEmail() {
        val email = "daniel@gmail.com"
        val password = "123456"

        // Simular comportamiento del repository para un correo ya registrado
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

    /**
     * Verifica que el inicio de sesión maneja correctamente los errores de red.
     */
    @Test
    fun testLoginUserNetworkError() {
        val email = "test@example.com"
        val password = "789893"

        // Simular comportamiento del repository para un error de red en el inicio de sesión
        Mockito.`when`(loginRepository.loginUser(eq(email), eq(password), any()))
            .thenAnswer { invocation ->
                val callback = invocation.getArgument<(Boolean) -> Unit>(2)
                // Simular respuesta es fallida debido a un error de red
                callback.invoke(false)
            }

        loginViewModel.loginUser(email, password) { isLogin ->
            assertFalse(isLogin)
        }
    }

    @Test
    fun testLogoutUserInactive() {
        // Simular comportamiento del repository para un cierre de sesión sin usuario activo
        Mockito.`when`(firebaseAuth.signOut())
            .thenThrow(RuntimeException("Simulated sign-out error"))

        // Perform the logout operation
        loginViewModel.logoutUser { success, error ->
            // Verify that the callback indicates failure and contains an error message
            assertFalse(success)
            assertNotNull(error)
        }
    }




}



