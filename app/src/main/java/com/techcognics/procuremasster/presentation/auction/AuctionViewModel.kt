package com.techcognics.procuremasster.presentation.auction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcognics.procuremasster.data.remote.auctionpackage.AuctionResponseItem
import com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit.BidPriceRequest
import com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit.SupplierBidDetailsItem
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

    private val _bidDetailsItem = MutableStateFlow<UiState<List<SupplierBidDetailsItem>>>(UiState.Idle)
    val bidDetailsItem: StateFlow<UiState<List<SupplierBidDetailsItem>>> = _bidDetailsItem


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

    fun loadBidDetails(rfqId: Int){
        viewModelScope.launch {
            try {
                val detailsItem = repository.getBidAuction(rfqId)
                println("DEBUG: Bid details API returned ${detailsItem.size} items") // << DEBUG PRINT
                _bidDetailsItem.value = UiState.Success(detailsItem)
            }
            catch (e: Exception){
                println("DEBUG: Bid details error: ${e.message}") // << DEBUG PRINT
                _bidDetailsItem.value = UiState.Error(e.message ?: "Failed to load Bid Auctions")
            }
        }
    }


    fun submitBidPrice(
        rfqId: Int,
        itemId: Int,
        bidPrice: Double,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = BidPriceRequest(itemId = itemId, price = bidPrice) // fixed line
                repository.submitBidPrice(rfqId, request)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Failed to submit bid")
            }
        }
    }




}

