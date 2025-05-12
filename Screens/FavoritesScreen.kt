package com.example.tourismappfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tourismappfinal.R
import com.example.tourismappfinal.ViewModel.ExploreViewModel
import com.example.tourismappfinal.data.entity.ExploreEntity
import com.example.tourismappfinal.ui.components.ExploreItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    exploreViewModel: ExploreViewModel,
    onNavigateToEdit: (ExploreEntity) -> Unit
) {
    val favoriteItems by exploreViewModel.favoriteItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.favorites)) }
            )
        }
    ) { paddingValues ->
        if (favoriteItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_favorites),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(favoriteItems) { item ->
                    ExploreItemCard(
                        item = item,
                        onEdit = { onNavigateToEdit(item) },
                        onDelete = { exploreViewModel.deleteExploreItem(item) },
                        onToggleFavorite = { exploreViewModel.toggleFavorite(item.id, !item.isFavorite) },
                        onItemClick = { onNavigateToEdit(item) }
                    )
                }
            }
        }
    }
} 