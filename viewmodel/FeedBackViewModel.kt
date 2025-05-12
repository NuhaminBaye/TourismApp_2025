package com.example.tourismappfinal.ViewModel



import androidx.lifecycle.ViewModel
import com.example.tourismappfinal.ui.theme.HomeLists
import com.example.tourismappfinal.ui.theme.getHomesLists
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FeedBackViewModel : ViewModel() {

    private val _hotels = MutableStateFlow(getHomesLists())
    val hotels: StateFlow<List<HomeLists>> = _hotels

    fun toggleLike(hotelId: Int) {
        _hotels.value = _hotels.value.map {
            if (it.id == hotelId) it.copy(isLiked = !it.isLiked) else it
        }
    }

    private val _rating = MutableStateFlow(1)
    val rating: StateFlow<Int> = _rating

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    fun setRating(value: Int) {
        _rating.value = value
    }

    fun setComment(text: String) {
        _comment.value = text
    }
}

