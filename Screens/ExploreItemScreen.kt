package com.example.tourismappfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tourismappfinal.R
import com.example.tourismappfinal.ViewModel.ExploreViewModel
import com.example.tourismappfinal.data.entity.ExploreEntity
import com.example.tourismappfinal.util.ValidationUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreItemScreen(
    exploreViewModel: ExploreViewModel,
    item: ExploreEntity? = null,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf(item?.title ?: "") }
    var description by remember { mutableStateOf(item?.description ?: "") }
    var imageUrl by remember { mutableStateOf(item?.imageUrl ?: "") }
    var rating by remember { mutableStateOf(item?.rating?.toString() ?: "") }
    var region by remember { mutableStateOf(item?.region ?: "") }
    var price by remember { mutableStateOf(item?.price ?: "") }

    var titleError by remember { mutableStateOf<String?>(null) }
    var descriptionError by remember { mutableStateOf<String?>(null) }
    var imageUrlError by remember { mutableStateOf<String?>(null) }
    var ratingError by remember { mutableStateOf<String?>(null) }
    var regionError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (item == null) stringResource(R.string.add_new) else stringResource(R.string.edit)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = null
                },
                label = { Text(stringResource(R.string.title)) },
                isError = titleError != null,
                supportingText = { titleError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    descriptionError = null
                },
                label = { Text(stringResource(R.string.description)) },
                isError = descriptionError != null,
                supportingText = { descriptionError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = imageUrl,
                onValueChange = {
                    imageUrl = it
                    imageUrlError = null
                },
                label = { Text(stringResource(R.string.image_url)) },
                isError = imageUrlError != null,
                supportingText = { imageUrlError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = rating,
                onValueChange = {
                    rating = it
                    ratingError = null
                },
                label = { Text(stringResource(R.string.rating)) },
                isError = ratingError != null,
                supportingText = { ratingError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = region,
                onValueChange = {
                    region = it
                    regionError = null
                },
                label = { Text(stringResource(R.string.region)) },
                isError = regionError != null,
                supportingText = { regionError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = price,
                onValueChange = {
                    price = it
                    priceError = null
                },
                label = { Text(stringResource(R.string.price)) },
                isError = priceError != null,
                supportingText = { priceError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = {
                    var isValid = true

                    if (!ValidationUtils.isValidRequiredField(title)) {
                        titleError = context.getString(R.string.error_required_field)
                        isValid = false
                    }

                    if (!ValidationUtils.isValidRequiredField(description)) {
                        descriptionError = context.getString(R.string.error_required_field)
                        isValid = false
                    }

                    if (!ValidationUtils.isValidImageUrl(imageUrl)) {
                        imageUrlError = context.getString(R.string.error_invalid_image_url)
                        isValid = false
                    }

                    val ratingValue = rating.toFloatOrNull()
                    if (ratingValue == null || !ValidationUtils.isValidRating(ratingValue)) {
                        ratingError = context.getString(R.string.error_invalid_rating)
                        isValid = false
                    }

                    if (!ValidationUtils.isValidRequiredField(region)) {
                        regionError = context.getString(R.string.error_required_field)
                        isValid = false
                    }

                    if (!ValidationUtils.isValidRequiredField(price)) {
                        priceError = context.getString(R.string.error_required_field)
                        isValid = false
                    }

                    if (isValid) {
                        val exploreItem = ExploreEntity(
                            id = item?.id ?: 0,
                            title = title,
                            description = description,
                            imageUrl = imageUrl,
                            rating = ratingValue!!,
                            isFavorite = item?.isFavorite ?: false,
                            region = region,
                            price = price
                        )

                        if (item == null) {
                            exploreViewModel.addExploreItem(exploreItem)
                        } else {
                            exploreViewModel.updateExploreItem(exploreItem)
                        }
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (item == null) stringResource(R.string.add) else stringResource(R.string.save))
            }
        }
    }
}