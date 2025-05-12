package com.example.mobileapppro

data class HomeLists(
    var id:Int,
    var title:String,
    var imageres:Int,
    var description:String,
    var isLiked: Boolean = false
)
fun getHomesLists():List<HomeLists>{
    return listOf<HomeLists>(
        HomeLists(1,"lalibel",R.drawable.lal,"Ancient traditions, vibrant festivals, and  warm hospitality. Experience a unique"),
        HomeLists(2,"Gonder Catsl",R.drawable.cas,"Discover Ethiopia's Ancient Architecture Axum And Lalibela's Timeless wonders."),
        HomeLists(3,"Axum",R.drawable.axu,"Axum is one of historical hertage in ethiopia"),
        HomeLists(4,"semen mountain",R.drawable.sem,"Discover Ethiopia's breathtaking landscapes from falls to desert to mountains"),
        HomeLists(5,"DebreDamo monastery",R.drawable.deb,"Ethiopia's festivals A journey into ancient religious traditions and vibrant culture."),
    )
}








