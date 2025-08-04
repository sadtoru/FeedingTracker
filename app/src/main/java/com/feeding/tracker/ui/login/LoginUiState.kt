package com.feeding.tracker.ui.login

import com.feeding.tracker.domain.model.UserDomain

sealed class LoginUiState {
    // 1. Un objeto para el estado inicial o de reposo.
    object Idle : LoginUiState()

    // 2. Un objeto para el estado de carga.
    object Loading : LoginUiState()

    // 3. Una clase de datos para el estado de éxito.
    //    Contiene los datos que la UI necesita mostrar al tener éxito.
    data class Success(val user: UserDomain) : LoginUiState()

    // 4. Una clase de datos para el estado de error.
    //    Contiene el mensaje de error que la UI necesita mostrar.
    data class Error(val message: String) : LoginUiState()
}