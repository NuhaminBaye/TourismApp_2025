package com.example.mobileapppro

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(viewModel: HomeViewModel = viewModel()) {
    val query by viewModel.query.collectAsState()
    val isSearchActive by viewModel.isSearchActive.collectAsState()
    val searchedQuery by viewModel.searchedQuery.collectAsState()
    val isSearched by viewModel.isSearched.collectAsState()
    val items = viewModel.items

    val liveSuggestions = if (query.isNotEmpty()) {
        items.filter { it.title.startsWith(query, ignoreCase = true) }
    } else items

    val searchResults = if (searchedQuery.isNotEmpty()) {
        items.filter { it.title.contains(searchedQuery, ignoreCase = true) }
    } else emptyList()

    val otherItems = items.filterNot { it.title.contains(searchedQuery, ignoreCase = true) }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = { viewModel.updateQuery(it) },
                    onSearch = { viewModel.performSearch() },
                    expanded = isSearchActive,
                    onExpandedChange = { viewModel.toggleSearchActive(it) },
                    leadingIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Person, contentDescription = "Mic")
                        }
                    },
                    placeholder = {
                        Text("Search For Hotels....")
                    },
                    trailingIcon = {
                        Row {
                            if (isSearchActive) {
                                IconButton(onClick = {
                                    if (query.isNotEmpty()) {
                                        viewModel.updateQuery("")
                                    } else {
                                        viewModel.resetSearch()
                                    }
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Close")
                                }
                            }
                            IconButton(onClick = { viewModel.performSearch() }) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        }
                    }
                )
            },
            expanded = isSearchActive,
            onExpandedChange = { viewModel.toggleSearchActive(it) },
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
                                    viewModel.updateQuery(item.title)
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

        Spacer(modifier = Modifier.height(16.dp))

        if (isSearched && searchedQuery.isNotEmpty()) {
            if (searchResults.isNotEmpty()) {
                LazyColumn {
                    items(searchResults + otherItems) { item ->
                        HomeCards(
                            item = item,
                            onLikeToggle = { viewModel.toggleLike(it) }
                        )
                    }
                }
            } else {
                Text("No results found for \"$searchedQuery\"", modifier = Modifier.padding(16.dp))
            }
        }

        if (!isSearchActive && !isSearched) {
            LazyColumn {
                items(items) { item ->
                    HomeCards(item = item, onLikeToggle = { viewModel.toggleLike(it) })
                }
            }
        }
    }
}

@Composable
fun HomeCards(item: HomeLists, onLikeToggle: (HomeLists) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
             colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            // Image with Like Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // Adjust height for rectangle
            ) {
                Image(
                    painter = painterResource(id = item.imageres),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                IconButton(
                    onClick = { onLikeToggle(item) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = if (item.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (item.isLiked) Color.Red else Color.White
                    )
                }
            }


        Spacer(modifier = Modifier.width(8.dp))

        // Title and Region

        Text(item.description, modifier = Modifier.padding(4.dp))


    }
}
}
