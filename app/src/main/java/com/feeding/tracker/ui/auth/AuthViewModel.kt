package com.feeding.tracker.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feeding.tracker.domain.model.UserDomain
import com.feeding.tracker.domain.useCase.LoginUseCase
import com.feeding.tracker.domain.useCase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthFormState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val nameError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val authSuccess: Boolean = false,
    val user: UserDomain? = null,
)

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val signUpUseCase: SignUpUseCase,
        private val loginUseCase: LoginUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
        val uiState: StateFlow<AuthUiState> = _uiState

        private val _formState = MutableStateFlow(AuthFormState())
        val formState: StateFlow<AuthFormState> = _formState

        fun onEmailChange(email: String) {
            _formState.update {
                it.copy(
                    email = email,
                    emailError = null, // Limpiar error cuando el usuario escribe
                )
            }
        }

        fun onPasswordChange(password: String) {
            _formState.update {
                it.copy(
                    password = password,
                    passwordError = null,
                )
            }
        }

        fun onConfirmPasswordChange(confirmPassword: String) {
            _formState.update {
                it.copy(
                    confirmPassword = confirmPassword,
                    confirmPasswordError = null,
                )
            }
        }

        fun onNameChange(displayName: String) {
            _formState.update {
                it.copy(
                    name = displayName,
                    nameError = null,
                )
            }
        }

//        fun signIn() {
//            if (!validateSignInInputs()) return
//
//            viewModelScope.launch {
//                _uiState.update { it.copy(isLoading = true) }
//
//                loginUseCase(_uiState.value.email, _uiState.value.password)
//                    .onSuccess { user ->
//                        _uiState.update {
//                            it.copy(
//                                isLoading = false,
//                                authSuccess = true,
//                                user = user,
//                            )
//                        }
//                    }.onFailure { exception ->
//                        _uiState.update {
//                            it.copy(
//                                isLoading = false,
//                                generalError = exception.message,
//                            )
//                        }
//                    }
//            }
//        }

        private fun isValidEmail(email: String): Boolean =
            android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()

        fun clearAuthSuccess() {
            _formState.update { it.copy(authSuccess = false) }
        }

        fun clearErrors() {
            _formState.update {
                it.copy(
                    emailError = null,
                    passwordError = null,
                    confirmPasswordError = null,
                    nameError = null,
                    generalError = null,
                )
            }
        }

        private fun validateSignUpInputs(): Boolean {
            val currentState = _formState.value
            var hasErrors = false

            Log.d("AuthViewModel", "Validating inputs...")
            // Validaciones de SignIn más las adicionales
            if (!validateSignInInputs()) hasErrors = true

            Log.d("AuthViewModel", "Validation results: hasErrors=$hasErrors")
            val displayNameError =
                when {
                    currentState.name.isBlank() -> {
                        hasErrors = true
                        "El nombre es requerido"
                    }

                    currentState.name.length < 2 -> {
                        hasErrors = true
                        "El nombre debe tener al menos 2 caracteres"
                    }

                    else -> null
                }

            val confirmPasswordError =
                when {
                    currentState.confirmPassword.isBlank() -> {
                        hasErrors = true
                        "Confirma tu contraseña"
                    }

                    currentState.confirmPassword != currentState.password -> {
                        hasErrors = true
                        "Las contraseñas no coinciden"
                    }

                    else -> null
                }

            _formState.update {
                it.copy(
                    nameError = displayNameError,
                    confirmPasswordError = confirmPasswordError,
                )
            }

            return !hasErrors
        }

        fun signUp() {
            Log.d("AuthViewModel", "Signing up...")
            if (!validateSignUpInputs()) return
            Log.d("AuthViewModel", "Validation passed, signing up...")

            viewModelScope.launch {
                _uiState.value = AuthUiState.Loading

                signUpUseCase(
                    email = _formState.value.email,
                    password = _formState.value.password,
//                    displayName = _uiState.value.displayName,
                ).onSuccess { user ->
                    _formState.update {
                        it.copy(
                            isLoading = false,
                            authSuccess = true,
                            user = user,
                        )
                    }
                    _uiState.value = AuthUiState.Success
                }.onFailure { exception ->
                    _uiState.value = AuthUiState.Error(exception.message ?: "Unknown error")
                }
            }
        }

        private fun validateSignInInputs(): Boolean {
            val currentState = _formState.value
            var hasErrors = false

            val emailError =
                when {
                    currentState.email.isBlank() -> {
                        hasErrors = true
                        "El email es requerido"
                    }

                    !isValidEmail(currentState.email) -> {
                        hasErrors = true
                        "Email inválido"
                    }

                    else -> null
                }

            val passwordError =
                when {
                    currentState.password.isBlank() -> {
                        hasErrors = true
                        "La contraseña es requerida"
                    }

                    currentState.password.length < 6 -> {
                        hasErrors = true
                        "La contraseña debe tener al menos 6 caracteres"
                    }

                    else -> null
                }

            _formState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError,
                )
            }

            return !hasErrors
        }
    }
