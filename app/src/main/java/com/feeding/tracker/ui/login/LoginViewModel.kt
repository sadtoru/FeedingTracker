package com.feeding.tracker.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feeding.tracker.domain.useCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onSignInClicked(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            loginUseCase(email, password)
                .collect { result ->
                    result.onSuccess { userDomain ->
                        _uiState.value = LoginUiState.Success(userDomain)
                    }.onFailure { exception ->
                        _uiState.value = LoginUiState.Error(exception.message ?: "Unknown error")
                    }
                }
        }
    }
}