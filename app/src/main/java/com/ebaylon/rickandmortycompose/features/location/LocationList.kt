package com.ebaylon.rickandmortycompose.features.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by: ebaylon.
 * Created on: 6/10/24.
 */

@Composable
fun LocationListScreen(
  onNavigateToLocationDetail: (locationId: Int) -> Unit
) {
  Column(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(text = "List of Locations")
    Spacer(modifier = Modifier.padding(vertical = 16.dp))
    Button(onClick = { onNavigateToLocationDetail(0) }) {
      Text("Go to Location Detail")
    }
  }
}