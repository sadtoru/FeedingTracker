package com.feeding.tracker.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feeding.tracker.ui.auth.AuthFormState
import com.feeding.tracker.ui.auth.AuthUiState
import com.feeding.tracker.ui.auth.AuthViewModel
import com.feeding.tracker.ui.components.FeedingInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onSingUpSuccess: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Scaffold proporciona la estructura de la pantalla
    Scaffold(
        topBar = {
            // Puedes tener un TopAppBar aquí si lo necesitas
            TopAppBar(
                title = { Text("SignUp") },
            )
        },
        snackbarHost = {
            // Un host para mostrar mensajes con Snackbar
            SnackbarHost(hostState = remember { SnackbarHostState() })
        },
    ) { innerPadding ->
        // Aquí va el contenido principal de tu pantalla de login
        // El innerPadding asegura que tu contenido no se superponga con las barras
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Aquí pones tus componentes:
            // - Campos de texto para email y contraseña
            // - Botón de login
            // - Indicador de carga (si es necesario)
            // - Mensaje de error

            when (uiState) {
                is AuthUiState.Idle -> {
                    // Mostrar el formulario de login y los botones
                    FeedingInput(
                        value = formState.name,
                        onValueChange = { viewModel.onNameChange(it) },
                        label = "Name",
                        error = formState.nameError,
                    )
                    FeedingInput(
                        value = formState.email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        label = "Email",
                        error = formState.emailError,
                    )
                    FeedingInput(
                        value = formState.password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = "Password",
                        isPassword = true,
                        error = formState.passwordError,
                    )
                    FeedingInput(
                        value = formState.confirmPassword,
                        onValueChange = { viewModel.onConfirmPasswordChange(it) },
                        label = "Confirm Password",
                        isPassword = true,
                        error = formState.confirmPasswordError,
                    )
                    Button(onClick = {
                        viewModel.signUp()
                    }) { Text("Sign Up") }
                }

                is AuthUiState.Loading -> {
                    // Mostrar un ProgressBar
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                    )
                }

                is AuthUiState.Success -> {
                    // Navegar a otra pantalla al tener éxito
                    LaunchedEffect(formState.authSuccess) {
                        if (formState.authSuccess) {
                            onSingUpSuccess()
                            viewModel.clearAuthSuccess()
                        }
                    }
                }

                is AuthUiState.Error -> {
                    LaunchedEffect(uiState) {
                        val errorMessage = (uiState as AuthUiState.Error).message
                        snackbarHostState.showSnackbar(errorMessage)
                    }
                }
            }

            // ... Otros componentes
        }
    }
}
