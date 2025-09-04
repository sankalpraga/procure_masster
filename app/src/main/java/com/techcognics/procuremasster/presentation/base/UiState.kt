package com.techcognics.procuremasster.presentation.base

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()        // default, no action yet
    object Loading : UiState<Nothing>()     // when API is being called
    data class Success<T>(val data: T) : UiState<T>()  // success with data
    data class Error(val message: String) : UiState<Nothing>() // error message
}
