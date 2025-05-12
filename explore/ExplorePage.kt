package com.example.mobileapppro

import android.app.DownloadManager.Query
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ExploreViewModel = viewModel()) {
    val query by viewModel.query.collectAsState()
    val isSearchActive by viewModel.isSearchActive.collectAsState()
    val searchedQuery by viewModel.searchedQuery.collectAsState()
    val isSearched by viewModel.isSearched.collectAsState()
    val items by viewModel.items.collectAsState()

    // Use items from ViewModel for all logic (instead of getHomeList())
    val liveSuggestions = if (query.isNotEmpty()) {
        items.filter { it.title.startsWith(query, ignoreCase = true) }
    } else items

    val searchResults = if (searchedQuery.isNotEmpty()) {
        items.filter { it.title.contains(searchedQuery, ignoreCase = true) }
    } else emptyList()

    val otherItems = items.filterNot { it.title.contains(searchedQuery, ignoreCase = true) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = viewModel::onQueryChange,
                    onSearch = { viewModel.performSearch() },
                    expanded = isSearchActive,
                    onExpandedChange = viewModel::toggleSearchActive,
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
                    }
                )
            },
            expanded = isSearchActive,
            onExpandedChange = viewModel::toggleSearchActive,
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

        if (isSearched && searchedQuery.isNotEmpty()) {
            if (searchResults.isNotEmpty()) {
                LazyColumn {
                    items(searchResults + otherItems) { item ->
                        HomeCard(item = item, onLikeToggle = viewModel::toggleLike)
                    }
                }
            } else {
                Text("No results found for \"$searchedQuery\"", Modifier.padding(16.dp))
            }
        }

        if (!isSearchActive && !isSearched) {
            LazyColumn {
                items(items) { item ->
                    HomeCard(item = item, onLikeToggle = viewModel::toggleLike)
                }
            }
        }
    }
}



@Composable
fun HomeCard(item: ExploreLists, onLikeToggle: (ExploreLists) -> Unit, onClick: () -> Unit = {}) {
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
                    modifier = Modifier
                        .weight(1.5f)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(item.star) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }

                    // Image with Like Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp) // Adjust height for rectangle
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
                                .align(Alignment.TopStart)
                                .padding(4.dp)
                        ) {
                            Icon(
                                imageVector = if (item.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Like",
                                tint = if (item.isLiked) Color.Red else Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Title and Region
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = item.title,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Region",
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.region,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Text("Service" , modifier = Modifier.padding(4.dp))
                    Row(modifier = Modifier.fillMaxWidth().padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Column() {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_cell_wifi_24), // Replace with actual image resource
                                contentDescription = "Wifi",
                                modifier = Modifier.size(25.dp)
                            )
                            Text("wifi", fontSize = 10.sp , fontWeight = FontWeight.SemiBold) }

                            Column {
                                Image(
                                    painter = painterResource(id = R.drawable.s1), // Replace with actual image resource
                                    contentDescription = "swimming Icon",
                                    modifier = Modifier.size(30.dp)
                                )
                                Text("Swim", fontSize = 10.sp , fontWeight = FontWeight.SemiBold)
                            }
                            Column {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_deck_24), // Replace with actual image resource
                                    contentDescription = "Cafe",
                                    modifier = Modifier.size(25.dp)
                                )
                                Text("cafe ", fontSize = 10.sp , fontWeight = FontWeight.SemiBold)
                            }
                            Column {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_directions_car_24), // Replace with actual image resource
                                    contentDescription = "parking",
                                    modifier = Modifier.size(25.dp)
                                )
                                Text("Parking", fontSize = 10.sp , fontWeight = FontWeight.SemiBold)
                            }
                    }
                }
            }

            // Price at Bottom
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.price,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }

}

