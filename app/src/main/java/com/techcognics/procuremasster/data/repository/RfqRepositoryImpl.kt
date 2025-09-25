package com.techcognics.procuremasster.data.repository

import android.content.Context
import android.net.Uri
import com.techcognics.procuremasster.data.remote.ApiService
import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.data.remote.dto.BidResponse
import com.techcognics.procuremasster.data.remote.dto.BidSaveRequest
import com.techcognics.procuremasster.data.remote.dto.BidSaveResponse
import com.techcognics.procuremasster.data.remote.dto.FreightTerms
import com.techcognics.procuremasster.data.remote.dto.RfqViewResponse
import com.techcognics.procuremasster.domain.repository.RfqRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

class RfqRepositoryImpl @Inject constructor(
    private val api: ApiService
): RfqRepository {
    override suspend fun getRfqsInBetween(startDate: String, endDate: String): List<RFQ> {
        return api.fetchRfqInBetween(startDate, endDate)
    }

    override suspend fun getRfqById(id: Int): RfqViewResponse {
        return api.getRfqById(id)
    }



    override suspend fun downloadPdf(rfqId: Int): ResponseBody = api.generateRfqPdf(rfqId)

    override suspend fun downloadAttachment(attachmentId: Int): ResponseBody =
        api.downloadAttachment(attachmentId)


    //Bid
    override suspend fun getBidDetails(rfqNumber: String): BidResponse =
        api.getBidDetails(rfqNumber)

    override suspend fun getFreightTerms(): List<FreightTerms> {
      return  api.getFreightTerms()
    }

    override suspend fun saveBid(request: BidSaveRequest): ResponseBody {
        return api.saveBid(request)
    }

    override suspend fun uploadAttachment(rfqId: Int, file: MultipartBody.Part): ResponseBody {
        return api.uploadAttachment(rfqId, file)
    }

    override suspend fun uploadAttachmentFromUri(rfqId: Int, fileUri: Uri, context: Context): ResponseBody {
        val resolver = context.contentResolver
        val inputStream = resolver.openInputStream(fileUri)
        val fileName = getFileName(resolver, fileUri)
        val bytes = inputStream?.readBytes() ?: ByteArray(0)
        val requestBody = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), bytes)
        val part = MultipartBody.Part.createFormData("file", fileName, requestBody)
        return api.uploadAttachment(rfqId, part)
    }

    private fun getFileName(resolver: android.content.ContentResolver, uri: Uri): String {
        var name = "unknown_file"
        val cursor = resolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex != -1) {
                name = it.getString(nameIndex)
            }
        }
        return name
    }



}