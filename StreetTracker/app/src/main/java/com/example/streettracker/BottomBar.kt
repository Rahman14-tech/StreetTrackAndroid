package com.example.streettracker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument

@Composable
fun BottomBar(navController: NavHostController){
    Scaffold(bottomBar = { BottomContent(navController)}) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxWidth()){
            NavHost(navController = navController, startDestination = Login.route){
                composable(Rumah.route){
                    HomeScreen(navController = navController,Repo.data)
                }
                composable(Login.route){
                    LoginScreen(navController = navController)
                }
                composable(Register.route) {
                    Registration(navController = navController)
                }
                composable(PostDetails.route + "/{${PostDetails.argPostId}}", arguments = listOf(
                    navArgument(PostDetails.argPostId) {type = NavType.IntType}
                )){
                    val id = requireNotNull(it.arguments?.getInt(PostDetails.argPostId)) { "Post id is null" }
                    PostDetails(navController = navController,id)
                }
                composable(Capture.route){
                    CaptureScreen(navController)
                }
                composable(ChangeInfo.route){
                    ChangeInfo(navController = navController)
                }
                composable(Congrat.route+ "/{${Congrat.argTobeDeleted}}", arguments = listOf(
                    navArgument(Congrat.argTobeDeleted) {type = NavType.IntType}
                )){
                    val id = requireNotNull(it.arguments?.getInt(Congrat.argTobeDeleted)) { "Post id is null" }
                    Congrats(navController = navController, id)
                }
                composable(Setting.route){
                    SettingScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun BottomContent(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destinationList = listOf(Rumah, Capture, Setting)
    val selectedIndex = rememberSaveable {
        mutableStateOf(0)
    }
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    when (navBackStackEntry?.destination?.route) {
        "Login"->bottomBarState.value = false
        "Register"->bottomBarState.value = false
        else -> bottomBarState.value = true

    }
    AnimatedVisibility(visible = bottomBarState.value) {
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.white),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(25.dp)
                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
        ) {
            destinationList.forEachIndexed{index, destination ->
                BottomNavigationItem(
                    label = {Text(text = destination.title)},
                    selected = index == selectedIndex.value,
                    icon = {
                        Icon(painter = painterResource(id = destination.icon),tint = Color.Unspecified, contentDescription = "Bottom Nav Icon", modifier = Modifier.fillMaxSize(0.5f))
                    },
                    onClick = {
                        selectedIndex.value = index
                        navController.navigate(destinationList[index].route) {
                            popUpTo(destinationList[index].route)
                            launchSingleTop = true
                        }
                    })
            }
        }
    }

}