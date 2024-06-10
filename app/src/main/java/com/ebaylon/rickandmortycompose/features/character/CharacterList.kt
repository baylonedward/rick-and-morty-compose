package com.ebaylon.rickandmortycompose.features.character

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
 * Created on: 6/8/24.
 */
@Composable
fun CharacterListScreen(
  onNavigateToCharacterDetail: (characterId: Int) -> Unit
) {
  Column(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(text = "List of Characters")
    Spacer(modifier = Modifier.padding(vertical = 16.dp))
    Button(onClick = { onNavigateToCharacterDetail(0) }) {
      Text("Go to Character Detail")
    }
  }
}