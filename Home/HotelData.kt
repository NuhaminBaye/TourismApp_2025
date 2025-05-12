import android.net.Uri

data class HotelData(
    val imageUri: Uri?,      // Image selected from local storage
    val name: String,        // Hotel name (e.g., "Hilton Garden Inn")
    val region: String,      // Region or location (e.g., "Addis Ababa")
    val rating: Float,       // Rating between 0.0 and 5.0
    val price: String      // Price per night (e.g., 1200.00)
)
