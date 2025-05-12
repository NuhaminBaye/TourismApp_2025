package com.example.tourismappfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tourismappfinal.R
import com.example.tourismappfinal.ViewModel.AuthViewModel
import com.example.tourismappfinal.ViewModel.ExploreViewModel
import com.example.tourismappfinal.data.entity.ExploreEntity
import com.example.tourismappfinal.ui.components.ExploreItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    exploreViewModel: ExploreViewModel,
    authViewModel: AuthViewModel,
    onNavigateToAdd: () -> Unit,
    onNavigateToEdit: (ExploreEntity) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val exploreItems by exploreViewModel.exploreItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.explore)) },
                actions = {
                    IconButton(onClick = { 
                        authViewModel.logout()
                        onNavigateToLogin()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = stringResource(R.string.logout)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_new)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(exploreItems) { item ->
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