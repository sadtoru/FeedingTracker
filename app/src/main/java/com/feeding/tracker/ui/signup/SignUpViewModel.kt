package com.feeding.tracker.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feeding.tracker.domain.useCase.SignUpUseCase
import com.feeding.tracker.ui.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isFormValid: Boolean = false,
)

@HiltViewModel
class SignUpViewModel
    @Inject
    constructor(
        private val signUpUseCase: SignUpUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
        val uiState: StateFlow<LoginUiState> = _uiState

        private val _loginFormState = MutableStateFlow(FormState())
        val state: StateFlow<FormState> = _loginFormState

        fun onEmailChange(email: String) {
            _loginFormState.update {
                it.copy(email = email)
            }
        }

        fun onPasswordChange(password: String) {
            _loginFormState.update {
                it.copy(password = password)
            }
        }

        fun onSignUpClicked(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _uiState.value = LoginUiState.Loading
                signUpUseCase(email, password)
                    .collect { result ->
                        result
                            .onSuccess { userDomain ->
                                _uiState.value = LoginUiState.Success(userDomain)
                            }.onFailure { exception ->
                                _uiState.value = LoginUiState.Error(exception.message ?: "Unknown error")
                            }
                    }
            }
        }
    }
