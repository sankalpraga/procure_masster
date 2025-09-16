package com.techcognics.procuremasster.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun saveFileToDownloads(context: Context, fileName: String, bytes: ByteArray): File {
    val downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    if (!downloads.exists()) downloads.mkdirs()

    val file = File(downloads, fileName)
    file.outputStream().use { it.write(bytes) }

    Toast.makeText(context, "Saved: ${file.absolutePath}", Toast.LENGTH_LONG).show()
    return file
}

fun openPdf(context: Context, file: File) {
    val uri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    context.startActivity(intent)
}
