package com.techcognics.procuremasster.presentation.auction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcognics.procuremasster.data.remote.auctionpackage.AuctionResponseItem
import com.techcognics.procuremasster.data.remote.auctionpackage.view.AuctionViewResponse
import com.techcognics.procuremasster.domain.repository.AuctionRepository
import com.techcognics.procuremasster.presentation.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuctionViewModel @Inject constructor(
    private val repository: AuctionRepository): ViewModel() {

    private val _listState = MutableStateFlow<UiState<List<AuctionResponseItem>>>(UiState.Idle)
    val listState: StateFlow<UiState<List<AuctionResponseItem>>> = _listState

    private val _auctionDetails = MutableStateFlow<AuctionViewResponse?>(null)
    val auctionDetails: StateFlow<AuctionViewResponse?> = _auctionDetails





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

        fun loadAuctionDetails(rfqId: Int) {
        viewModelScope.launch {
            try {
                val details = repository.getRfqByIdAuction(rfqId)
                _auctionDetails.value = details
            } catch (e: Exception) {
                _auctionDetails.value = null // handle error
            }
        }
    }

    fun downloadBidHistory(rfqId: Int, onDownloaded: (ByteArray) -> Unit){
        viewModelScope.launch {
            try {
                val response = repository.getBidHistory(rfqId)
                onDownloaded(response.bytes())
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


}