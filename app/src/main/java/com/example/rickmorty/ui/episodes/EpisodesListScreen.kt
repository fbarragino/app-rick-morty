package com.example.rickmorty.ui.episodes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Episodes (Fake)") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding)
        ) { }

    }
}