package com.techcognics.procuremasster.presentation.rfqdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techcognics.procuremasster.data.remote.dto.RfqItem


@Composable
fun ItemCard(item : RfqItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(item.description, style = MaterialTheme.typography.bodyMedium)
            Text("Qty: ${item.itemNumber} ${item.uom.name}",
                style = MaterialTheme.typography.labelSmall)
        }
    }
}