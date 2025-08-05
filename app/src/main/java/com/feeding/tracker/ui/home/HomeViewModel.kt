package com.feeding.tracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feeding.tracker.domain.useCase.AuthUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserState(
    val name: String = "",
    val email: String = "",
)

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val currentUser: AuthUserUseCase,
    ) : ViewModel() {
        private var _userUiState = MutableStateFlow<UserState?>(UserState())
        val userUiState = _userUiState

        init {
            viewModelScope.launch {
                currentUser().collect { user ->
                    if (user != null) {
                        // User is logged in
                        userUiState.update {
                            it?.copy(
                                email = user.email,
                            )
                        }
                    } else {
                        // User is not logged in
                    }
                }
            }
        }
    }
