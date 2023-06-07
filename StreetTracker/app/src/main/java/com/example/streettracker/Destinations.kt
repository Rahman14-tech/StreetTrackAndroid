package com.example.streettracker

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

interface Destinations {
    val route: String
    val icon: Int
    val title: String
}

object Rumah : Destinations {
    override val route = "Rumah"
    override val icon = R.drawable.home
    override val title = "Home"
}

object Capture : Destinations {
    override val route = "Capture"
    override val icon = R.drawable.camera
    override val title = "Capture"
}
object ChangeInfo : Destinations {
    override val route = "ChangeInfo"
    override val icon = R.drawable.camera
    override val title = "ChangeInfo"
}
object Setting : Destinations {
    override val route = "Setting"
    override val icon = R.drawable.setting
    override val title = "Setting"
}

object PostDetails : Destinations {
    override val route = "Menu"
    const val argPostId = "postId"
    override val icon = R.drawable.setting
    override val title = "Post Detail"
    @OptIn(ExperimentalPermissionsApi::class)
    var mapPermission : PermissionState? = null
}
object Congrat : Destinations {
    override val route = "Congrat"
    const val argTobeDeleted = "deleteId"
    override val icon = R.drawable.setting
    override val title = "Congrat"
}
object Login : Destinations {
    override val route = "Login"
    override val icon = R.drawable.home
    override val title = "Login"
}
object Register : Destinations {
    override val route = "Register"
    override val icon = R.drawable.home
    override val title = "Register"
}
