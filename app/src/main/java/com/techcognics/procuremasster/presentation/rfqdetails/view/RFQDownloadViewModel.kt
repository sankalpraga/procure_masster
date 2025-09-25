package com.techcognics.procuremasster.presentation.rfqdetails.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcognics.procuremasster.domain.repository.RfqRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RFQDownloadViewModel @Inject constructor(
    private val repository: RfqRepository
) : ViewModel() {

    fun downloadRfqPdf(rfqId: Int, onDownloaded: (ByteArray) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.downloadPdf(rfqId)
                onDownloaded(response.bytes())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun downloadAttachment(attachmentId: Int, onDownloaded: (ByteArray) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.downloadAttachment(attachmentId)
                onDownloaded(response.bytes())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}