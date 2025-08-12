package com.feeding.tracker.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feeding.tracker.domain.useCase.LoginUseCase
import com.feeding.tracker.ui.auth.AuthFormState
import com.feeding.tracker.ui.auth.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val loginUseCase: LoginUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
        val uiState: StateFlow<AuthUiState> = _uiState

        fun onSignInClicked(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _uiState.value = AuthUiState.Loading
                loginUseCase(email, password)
                    .collect { result ->
                        result
                            .onSuccess { userDomain ->
                                _uiState.value = AuthUiState.Success
                            }.onFailure { exception ->
                                _uiState.value = AuthUiState.Error(exception.message ?: "Unknown error")
                            }
                    }
            }
        }
    }
