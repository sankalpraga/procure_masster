package com.techcognics.procuremasster.presentation.rfqdetails.bid.viewModel

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcognics.procuremasster.data.remote.ApiService
import com.techcognics.procuremasster.data.remote.dto.*
import com.techcognics.procuremasster.domain.repository.RfqRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BidViewModel @Inject constructor(
    private val repository: RfqRepository,
    private val api: ApiService
) : ViewModel() {

    var bidResponse by mutableStateOf<BidResponse?>(null)
        private set

    var pricingItems by mutableStateOf<List<ItemBidingPricing>>(emptyList())
        private set

    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var packingForwarding by mutableStateOf<Double?>(null)
    var freightTermsOptions by mutableStateOf<List<FreightTerms>>(emptyList())
        private set
    var selectedFreightTerm by mutableStateOf<FreightTerms?>(null)
    var freightCharges by mutableStateOf<Double?>(null)
    var paymentSchedule by mutableStateOf("")
    var specialInstructions by mutableStateOf("")
    var comments by mutableStateOf("")
    var attachments by mutableStateOf<List<Attachment>>(emptyList())
        private set
    var supplierDepositoryId by mutableStateOf(-1)
    var rfqId by mutableStateOf(-1)

    fun loadBidDetails(rfqNumber: String) {
        loading = true
        viewModelScope.launch {
            try {
                val response = repository.getBidDetails(rfqNumber)
                bidResponse = response
                pricingItems = response.itemBidingPricingList
                supplierDepositoryId = response.supplierDepositoryId
                rfqId = response.rfqId
                error = null
            } catch (e: Exception) {
                error = e.localizedMessage ?: "Loading error"
            }
            loading = false
        }
    }

    fun fetchFreightTerms() {
        viewModelScope.launch {
            try {
                val terms = api.getFreightTerms()
                freightTermsOptions = terms
//                selectedFreightTerm = terms.firstOrNull()
            } catch (e: Exception) {
                freightTermsOptions = emptyList()
            }
        }
    }

    fun updatePricingItem(updated: ItemBidingPricing) {
        pricingItems = pricingItems.map {
            if (it.itemId == updated.itemId) updated else it
        }
        bidResponse = bidResponse?.copy(itemBidingPricingList = pricingItems)
    }

    fun addAttachment(new: Attachment) { attachments = attachments + new }
    fun removeAttachment(att: Attachment) { attachments = attachments - att }
    fun clearAttachments() { attachments = emptyList() }

    fun uploadAttachments(context: Context, rfqId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                attachments.forEach { att ->
                    repository.uploadAttachmentFromUri(rfqId, att.uri, context)
                }
                onResult(true)
            } catch (e: Exception) {
                error = e.message
                onResult(false)
            }
        }
    }

    fun saveBid(onResult: (Boolean) -> Unit) {
        if (rfqId == -1 || supplierDepositoryId == -1) {
            error = "RFQ ID or Supplier Depository ID missing/invalid!"
            onResult(false)
            return
        }
        viewModelScope.launch {
            try {
                // Ensure all pricing items have rfqId set
                val pricingItemsReq = pricingItems.map { it.copy(rfqId = rfqId) }
                val saveRequest = BidSaveRequest(
                    rfqId = rfqId,
                    supplierDepositoryId = supplierDepositoryId,
                    packingForwarding = packingForwarding ?: 0.0,
                    freightTerms = selectedFreightTerm ?: error("No freight term selected"),
                    freightCharges = freightCharges ?: 0.0,
                    paymentSchedule = paymentSchedule,
                    specialInstructionToBid = specialInstructions,
                    comments = comments,
                    itemBidingPricingList = pricingItemsReq
                )
                repository.saveBid(saveRequest)
                onResult(true)
            } catch (e: Exception) {
                error = e.localizedMessage
                onResult(false)
            }
        }
    }
}
