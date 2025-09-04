//package com.techcognics.procuremasster.presentation.rfqdetails
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.techcognics.procuremasster.data.remote.dto.RfqResponse
//import com.techcognics.procuremasster.domain.repository.RfqRepository
//import com.techcognics.procuremasster.presentation.base.UiState
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class RFQViewModel @Inject constructor(
//    private val rfqRepository: RfqRepository
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow<UiState<List<RfqResponse>>>(UiState.Idle)
//    val uiState: StateFlow<UiState<List<RfqResponse>>> = _uiState
//
//    private val _startDate = MutableStateFlow("")
//    val startDate: StateFlow<String> = _startDate
//
//    private val _endDate = MutableStateFlow("")
//    val endDate: StateFlow<String> = _endDate
//
//    fun updateDateRange(start: String, end: String) {
//        _startDate.value = start
//        _endDate.value = end
//        loadRfqs(start, end)
//    }
//
//    private fun loadRfqs(start: String, end: String) {
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            try {
//                val rfqs = rfqRepository.fetchRfqs(start, end)
//                _uiState.value = UiState.Success(rfqs)
//            } catch (e: Exception) {
//                _uiState.value = UiState.Error(e.message ?: "Failed to load RFQs")
//            }
//        }
//    }
//}