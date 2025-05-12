package com.example.tourismappfinal.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tourismappfinal.R
import com.example.tourismappfinal.ViewModel.ExploreViewModel
import com.example.tourismappfinal.data.entity.ExploreEntity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ExploreViewModel = viewModel(),
    onNavigateToExplore: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
//    val database = remember { AppDatabase.getDatabase(LocalContext.current) }
//    val repository = remember { ExploreRepository(database.exploreDao()) }
//    val viewModel: ExploreViewModel = viewModel(
//        factory = ExploreViewModel.Factory(repository)
//    )

    val query by viewModel.query.collectAsState()
    val isSearchActive by viewModel.isSearchActive.collectAsState()
    val searchedQuery by viewModel.searchedQuery.collectAsState()
    val isSearched by viewModel.isSearched.collectAsState()
    val exploreItems by viewModel.exploreItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val liveSuggestions = if (query.isNotEmpty()) {
        exploreItems.filter { it.title.startsWith(query, ignoreCase = true) }
    } else exploreItems

    val searchResults = if (searchedQuery.isNotEmpty()) {
        exploreItems.filter { it.title.contains(searchedQuery, ignoreCase = true) }
    } else emptyList()

    val otherItems = exploreItems.filterNot { it.title.contains(searchedQuery, ignoreCase = true) }

    // Show error if any
    error?.let { errorMessage ->
        LaunchedEffect(errorMessage) {
            viewModel.clearError()
        }
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("Dismiss")
                }
            }
        ) {
            Text(errorMessage)
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        SearchBar(
            query = query,
            onQueryChange = viewModel::onQueryChange,
            onSearch = { viewModel.performSearch() },
            active = isSearchActive,
            onActiveChange = { viewModel.toggleSearchActive() },
            leadingIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Person, null) } },
            placeholder = { Text("Search For Hotels....") },
            trailingIcon = {
                Row {
                    if (isSearchActive) {
                        IconButton(onClick = {
                            if (query.isNotEmpty()) {
                                viewModel.onQueryChange("")
                            } else {
                                viewModel.clearSearch()
                            }
                        }) {
                            Icon(Icons.Default.Close, null)
                        }
                    }
                    IconButton(onClick = { viewModel.performSearch() }) {
                        Icon(Icons.Default.Search, null)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 4.dp,
            shadowElevation = 0.dp,
            windowInsets = WindowInsets(0, 0, 0, 0)
        ) {
            if (query.isNotEmpty()) {
                LazyColumn {
                    items(liveSuggestions) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    viewModel.onQueryChange(item.title)
                                    viewModel.performSearch()
                                },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Text(item.title, Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (isSearched && searchedQuery.isNotEmpty()) {
                if (searchResults.isNotEmpty()) {
                    LazyColumn {
                        items(searchResults + otherItems) { item ->
                            HomeCard(
                                item = item,
                                onFavoriteToggle = { viewModel.toggleFavorite(it.id, !it.isFavorite) }
                            )
                        }
                    }
                } else {
                    Text("No results found for \"$searchedQuery\"", Modifier.padding(16.dp))
                }
            }

            if (!isSearchActive && !isSearched) {
                if (exploreItems.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hotels available")
                    }
                } else {
                    LazyColumn {
                        items(exploreItems) { item ->
                            HomeCard(
                                item = item,
                                onFavoriteToggle = { viewModel.toggleFavorite(it.id, !it.isFavorite) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeCard(
    item: ExploreEntity,
    onFavoriteToggle: (ExploreEntity) -> Unit,
    onClick: () -> Unit = {}
) {
    var isFavorite by remember { mutableStateOf(item.isFavorite) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1.5f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(item.rating.toInt()) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    ) {
                        Image(
                            painter = painterResource(id = item.imageResId ?: R.drawable.hu),
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        IconButton(
                            onClick = { 
                                isFavorite = !isFavorite
                                onFavoriteToggle(item)
                            },
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(4.dp)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Like",
                                tint = if (isFavorite) Color.Red else Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = item.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.description,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.region,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.price,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
            }
        }
    }
} 