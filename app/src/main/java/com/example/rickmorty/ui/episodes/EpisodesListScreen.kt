package com.example.rickmorty.ui.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickmorty.data.EpisodeDTO
import com.example.rickmorty.ui.state.UiState
import com.example.rickmorty.ui.theme.*

@Composable
fun EpisodesListScreen() {
    val vm = remember { EpisodesListViewModel() }
    val state by vm.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.load()
    }

    Scaffold(
        topBar = { RickMortyTopBar() },
        bottomBar = { RickMortyBottomBar() },
        containerColor = RickDarkBlue
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (val s = state) {
                UiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = RickPrimary)
                }
                is UiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${s.message}", color = Color.White)
                }
                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        item { FeaturedEpisodeCard() }
                        item { FilterChips() }
                        item { RecentEpisodesHeader() }
                        items(s.data) { episode ->
                            EpisodeListItem(episode)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickMortyTopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = RickDarkBlue,
            titleContentColor = Color.White
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(RickSurface)
                        .border(1.dp, RickPrimary.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = null,
                        tint = RickPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    buildAnnotatedString {
                        append("Rick and ")
                        withStyle(style = SpanStyle(color = RickPrimary)) {
                            append("Morty")
                        }
                    },
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = RickTextSecondary)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = RickTextSecondary)
            }
        }
    )
}

@Composable
fun FeaturedEpisodeCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(RickSurface)
            .border(1.dp, RickPrimary.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
    ) {
        // Simulating the portal background with a gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(RickAccent.copy(alpha = 0.4f), Color.Transparent),
                        radius = 400f
                    )
                )
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = "Todos los espisodios\nde la serie",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                ),
                color = Color.White
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Revisa los episodios actualizados de cada temporada\nRevive una y otra vez los mejores momentos...",
                style = MaterialTheme.typography.bodySmall,
                color = RickTextSecondary,
                maxLines = 2
            )
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = RickPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Ver ahora", fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Spacer(Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun FilterChips() {
    val filters = listOf("Todos los episodios", "Temporada 1", "Temporada 2")
    var selectedFilter by remember { mutableStateOf("Todos los episodios") }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(filters) { filter ->
            val isSelected = filter == selectedFilter
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (isSelected) RickPrimary else Color.Transparent)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) RickPrimary else RickSurface,
                        shape = CircleShape
                    )
                    .clickable { selectedFilter = filter }
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = filter,
                    color = if (isSelected) Color.Black else RickPrimary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun RecentEpisodesHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Episodios recientes",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
        Text(
            text = "Ver todos",
            style = MaterialTheme.typography.bodyMedium,
            color = RickPrimary,
            modifier = Modifier.clickable { }
        )
    }
}

@Composable
fun EpisodeListItem(episode: EpisodeDTO) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Thumbnail Placeholder
        Box(
            modifier = Modifier
                .size(width = 120.dp, height = 80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(RickSurface)
        ) {
            // Simulated image with a gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(RickSurface, RickDarkBlue)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = RickTextSecondary.copy(alpha = 0.3f))
            }

            // Duration tag
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text("22:04", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = episode.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                maxLines = 1
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = episode.episode, // This contains "S01E01" style usually
                style = MaterialTheme.typography.bodySmall,
                color = RickTextSecondary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "8.4M views • ${episode.air_date}",
                style = MaterialTheme.typography.bodySmall,
                color = RickTextSecondary
            )
        }

        IconButton(onClick = {}) {
            Icon(Icons.Default.MoreVert, contentDescription = null, tint = RickTextSecondary)
        }
    }
}

@Composable
fun RickMortyBottomBar() {
    NavigationBar(
        containerColor = RickBottomBar,
        contentColor = RickTextSecondary,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Inicio") },
            selected = false,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = RickTextSecondary,
                unselectedTextColor = RickTextSecondary,
                selectedIconColor = RickPrimary,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.PlayArrow, contentDescription = null) },
            label = { Text("Episodios") },
            selected = true,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = RickPrimary,
                selectedTextColor = RickPrimary,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Personajes") },
            selected = false,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = RickTextSecondary,
                unselectedTextColor = RickTextSecondary,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = null) },
            label = { Text("Guardado") },
            selected = false,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = RickTextSecondary,
                unselectedTextColor = RickTextSecondary,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Opciones") },
            selected = false,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = RickTextSecondary,
                unselectedTextColor = RickTextSecondary,
                indicatorColor = Color.Transparent
            )
        )
    }
}
