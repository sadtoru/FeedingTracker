package com.feeding.tracker.ui.auth

sealed class AuthUiState {
    // 1. Un objeto para el estado inicial o de reposo.
    object Idle : AuthUiState()

    // 2. Un objeto para el estado de carga.
    object Loading : AuthUiState()

    // 3. Una clase de datos para el estado de éxito.
    //    Contiene los datos que la UI necesita mostrar al tener éxito.
    object Success : AuthUiState()

    // 4. Una clase de datos para el estado de error.
    //    Contiene el mensaje de error que la UI necesita mostrar.
    data class Error(
        val message: String,
    ) : AuthUiState()
}
