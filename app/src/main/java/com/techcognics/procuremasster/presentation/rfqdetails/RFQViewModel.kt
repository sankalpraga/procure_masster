package com.techcognics.procuremasster.presentation.rfqdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.domain.repository.RfqRepository
import com.techcognics.procuremasster.presentation.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RFQViewModel @Inject constructor(private val repository: RfqRepository)
    : ViewModel() {

    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val today = sdf.format(Date())
    private val _startDate = MutableStateFlow(today)
    val startDate: StateFlow<String> = _startDate

    private val _endDate = MutableStateFlow(today)
    val endDate: StateFlow<String> = _endDate

    private val _uistate = MutableStateFlow<UiState<List<RFQ>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<RFQ>>> = _uistate

//    private val _rfqs = MutableStateFlow<List<RFQ>>(emptyList())
//    val rfqs: StateFlow<List<RFQ>> = _rfqs
//
//    private val _loading = MutableStateFlow(false)
//    val loading: StateFlow<Boolean> = _loading
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error
//
    init {
        loadRfqs(today, today)
    }

    fun updateDateRange(start: String, end: String){
        _startDate.value = start
        _endDate.value = end
        loadRfqs(start, end)
    }

    private fun loadRfqs(startDate: String, endDate: String)
    {
     viewModelScope.launch {
         _uistate.value = UiState.Loading
         try {
             val rfqs = repository.getRfqsInBetween(startDate, endDate)
             _uistate.value = UiState.Success(rfqs)
         }
         catch (e: Exception){
             _uistate.value = UiState.Error(e.message ?: "Failed to load RFqs")
         }
     }
    }

    fun resetState(){
        _uistate.value = UiState.Idle
    }

}




