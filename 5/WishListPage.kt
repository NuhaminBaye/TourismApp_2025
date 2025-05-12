
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import com.example.mobileapppro.ExploreViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapppro.HomeCard


@Composable
fun WishList(viewModel: ExploreViewModel = viewModel()) {
    val items by viewModel.items.collectAsState()
    val likedItems = items.filter { it.isLiked }

    LazyColumn {
        items(likedItems) { item ->
            HomeCard(
                item = item,
                onLikeToggle = viewModel::toggleLike
            )
        }
    }
}

