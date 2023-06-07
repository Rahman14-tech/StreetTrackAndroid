package com.example.streettracker

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun SettingScreen(navController: NavHostController){
    if(UserRepo.usernameCookies == ""){
        navController.navigate(Login.route) {
            popUpTo(Login.route) {
                inclusive = true
            }
        }
    }
    val post = requireNotNull(UserRepo.getUserbyUsername(UserRepo.usernameCookies))
    if(post != null){
        Column(modifier = Modifier.fillMaxSize(0.97f)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "User Information", modifier = Modifier.padding(5.dp), fontWeight = FontWeight.ExtraBold, color = Color(0xFF00B0FF), fontSize = 30.sp)
            }
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Black
            )
            Text(text = "Username: ${post.username}", modifier = Modifier.padding(5.dp), fontWeight = FontWeight.Bold, color = Color(0xFF025071), fontSize = 20.sp)
            Text(text = "Email: ${post.email}", modifier = Modifier.padding(5.dp), fontWeight = FontWeight.Bold, color = Color(0xFF025071), fontSize = 20.sp)
            Text(text = "User Type: ${post.Kepemerintahan}", modifier = Modifier.padding(top = 5.dp, end = 5.dp, start = 5.dp, bottom = 20.dp), fontWeight = FontWeight.Bold, color = Color(0xFF025071), fontSize = 20.sp)
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = {

                },border = BorderStroke(1.dp, Color(0xFF00B0FF)), shape = RoundedCornerShape(20), modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(horizontal = 0.dp, vertical = 5.dp)) {
                    Text(text = "See Your KTP", color = Color(0xFF00B0FF), fontWeight = FontWeight.ExtraBold)
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = {
                    navController.navigate(ChangeInfo.route)
                },border = BorderStroke(1.dp, Color(0xFF986DF2)), shape = RoundedCornerShape(20), modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(horizontal = 0.dp, vertical = 5.dp)) {
                    Text(text = "Change your user info", color = Color(0xFF986DF2), fontWeight = FontWeight.ExtraBold)
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = {
                    navController.navigate(Login.route) {
                        popUpTo(Login.route) {
                            inclusive = true
                        }
                    }
                },border = BorderStroke(1.dp, Color(0xFFFF1010)), shape = RoundedCornerShape(20), modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(horizontal = 0.dp, vertical = 5.dp)) {
                    Text(text = "Logout", color = Color(0xFFFF1010), fontWeight = FontWeight.ExtraBold)
                }
            }
        }
    }

}