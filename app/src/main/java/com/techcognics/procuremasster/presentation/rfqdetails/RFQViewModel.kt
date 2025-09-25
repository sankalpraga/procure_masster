package com.techcognics.procuremasster.presentation.rfqdetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.data.remote.dto.RfqViewResponse
import com.techcognics.procuremasster.domain.repository.RfqRepository
import com.techcognics.procuremasster.presentation.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.Q)
@HiltViewModel
class RFQViewModel @Inject constructor(
    private val repository: RfqRepository
) : ViewModel() {

    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val today = sdf.format(Date())

    @RequiresApi(Build.VERSION_CODES.Q)
    private val lastMonth: String = Calendar.getInstance().apply {
        add(Calendar.MONTH, -1)
    }.time.let { sdf.format(it) }


    // --- Date range state for list ---
    private val _startDate = MutableStateFlow(today)
    val startDate: StateFlow<String> = _startDate

    private val _endDate = MutableStateFlow(today)
    val endDate: StateFlow<String> = _endDate

    // --- RFQ list state ---
    private val _listState = MutableStateFlow<UiState<List<RFQ>>>(UiState.Idle)
    val listState: StateFlow<UiState<List<RFQ>>> = _listState

    // --- RFQ detail state ---
    private val _detailState = MutableStateFlow<UiState<RfqViewResponse>>(UiState.Idle)
    val detailState: StateFlow<UiState<RfqViewResponse>> = _detailState

//    private val _bidState = MutableStateFlow<UiState<RFQBidResponse>>(UiState.Idle)
//    val bidState: StateFlow<UiState<RFQBidResponse>> = _bidState

    init {
        // Load today's RFQs by default
        loadRfqs(lastMonth, today)
    }

    // 🔹 Fetch RFQs between dates
    private fun loadRfqs(startDate: String, endDate: String) {
        viewModelScope.launch {
            _listState.value = UiState.Loading
            try {
                val rfqs = repository.getRfqsInBetween(startDate, endDate)
                _listState.value = UiState.Success(rfqs)
            } catch (e: Exception) {
                _listState.value = UiState.Error(e.message ?: "Failed to load RFQs")
            }
        }
    }

    fun updateDateRange(start: String, end: String) {
        _startDate.value = start
        _endDate.value = end
        loadRfqs(start, end)
    }

    // 🔹 Fetch a single RFQ by ID
    fun loadRfqById(id: Int) {
        viewModelScope.launch {
            println("➡️ ViewModel calling RFQ detail with id=$id")
            _detailState.value = UiState.Loading
            try {
                val rfq = repository.getRfqById(id)
                _detailState.value = UiState.Success(rfq)
            } catch (e: Exception) {
                println("❌ API Error: ${e.message}")
                _detailState.value = UiState.Error(e.message ?: "Failed to load RFQ details")
            }
        }
    }


//    fun loadBidByNumber(rfqNumber: String)
//    = viewModelScope.launch {
//        println("ViewModel loading RFQ with id = $rfqNumber")
//        _bidState.value = UiState.Loading
//        try {
//            val rfq = repository.getBidDetails(rfqNumber) // should return RfqViewData
//            _bidState.value = UiState<RFQBidResponse>
//        } catch (e: Exception) {
//            println("API Error: ${e.message}")
//            _bidState.value = UiState.Error(e.message ?: "Failed to load RFQ details")
//        }
//    }

    // Optional resets
    fun resetListState() {
        _listState.value = UiState.Idle
    }

    fun resetDetailState() {
        _detailState.value = UiState.Idle
    }
}