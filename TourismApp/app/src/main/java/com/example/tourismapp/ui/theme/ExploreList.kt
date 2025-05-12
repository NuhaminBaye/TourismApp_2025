package com.example.tourismapp.ui.theme
import com.example.tourismapp.R

data class ExploreLists(
    var id:Int,
    var title:String,
    var region:String,
    var price:String,
    var imageres:Int,
    var star:Int,
    var isLiked: Boolean = false
)
fun getHomeList():List<ExploreLists>{
    return listOf<ExploreLists>(
        ExploreLists(1,"lodge du chateau","Somali,Jgjiga","$180",R.drawable.p,5),
        ExploreLists(2,"Hilton","Addis Ababa","$190",R.drawable.p,4),
        ExploreLists(3,"Haile Resort ","Sidama,Hawassa","$170",R.drawable.p,3),
        ExploreLists(4,"Planet Hotel","Tigray, Mekele","$190",R.drawable.p,2),
        ExploreLists(5,"Inn of the Four Sisters hotel","Amhara, Gonder","$160",R.drawable.p,2)
    )
}








