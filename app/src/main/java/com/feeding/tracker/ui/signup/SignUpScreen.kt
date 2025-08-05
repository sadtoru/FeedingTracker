package com.feeding.tracker.ui.signup

import ads_mobile_sdk.h4
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feeding.tracker.domain.model.UserDomain
import com.feeding.tracker.ui.login.LoginUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onSingUpSuccess: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formState by viewModel.state.collectAsStateWithLifecycle()
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
                is LoginUiState.Idle -> {
                    // Mostrar el formulario de login y los botones
                    TextField(value = formState.email, onValueChange = { viewModel.onEmailChange(it) })
                    TextField(
                        value = formState.password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                    )
                    Button(onClick = {
                        viewModel.onSignUpClicked(
                            formState.email,
                            formState.password,
                        )
                    }) { Text("Sign Up") }
                }

                is LoginUiState.Loading -> {
                    // Mostrar un ProgressBar
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                    )
                }

                is LoginUiState.Success -> {
                    // Navegar a otra pantalla al tener éxito
                    onSingUpSuccess()
                }

                is LoginUiState.Error -> {
                    LaunchedEffect(uiState) {
                        val errorMessage = (uiState as LoginUiState.Error).message
                        snackbarHostState.showSnackbar(errorMessage)
                    }
                }
            }

            // ... Otros componentes
        }
    }
}
