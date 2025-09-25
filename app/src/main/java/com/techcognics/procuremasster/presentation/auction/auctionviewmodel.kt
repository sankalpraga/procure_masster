package com.techcognics.procuremasster.presentation.auction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.techcognics.procuremasster.data.Auction.AuctionResponseItem
import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.domain.repository.AuctionRepository
import com.techcognics.procuremasster.presentation.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class auctionviewmodel @Inject constructor(
    private val repository: AuctionRepository): ViewModel() {

    private val _listState = MutableStateFlow<UiState<List<AuctionResponseItem>>>(UiState.Idle)
    val listState: StateFlow<UiState<List<AuctionResponseItem>>> = _listState

    init {
        loadAuctions()
    }

    private fun loadAuctions(){
        viewModelScope.launch {
            _listState.value = UiState.Loading
            try {
                val auctions = repository.getAuctionDetails()
                _listState.value = UiState.Success(auctions)
            }
            catch (e: Exception) {
                _listState.value = UiState.Error(e.message ?: "Failed to load Auctions")

            }
        }
    }
}