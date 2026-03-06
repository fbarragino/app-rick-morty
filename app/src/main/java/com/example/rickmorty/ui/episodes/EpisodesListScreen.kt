package com.example.rickmorty.ui.episodes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickmorty.data.EpisodeDTO
import com.example.rickmorty.ui.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesListScreen() {

    // Creamos el VM "manual" (para mantenerlo simple)
    val vm = remember { EpisodesListViewModel() }

    // convertir el StateFlow en "State, para que compose pueda observar
    val state by vm.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.load()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Episodes (Fake)") }
            )
        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val s = state) {
                UiState.Loading -> CircularProgressIndicator()
                is UiState.Error -> Text("Error: ${s.message}")
                is UiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        items(s.data) { episode: EpisodeDTO ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {

                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = episode.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "${episode.episode} · ${episode.air_date}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }


                        }
                    }
                }
            }
        }

    }
}