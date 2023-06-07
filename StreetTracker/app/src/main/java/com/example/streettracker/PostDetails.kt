package com.example.streettracker

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import java.util.logging.Handler

@Composable
fun PostDetails(navController : NavHostController,id : Int){
    val post = requireNotNull(Repo.getPost(id))
    val Context = LocalContext.current
    var approveOpen by remember {
        mutableStateOf(false)
    }
    var disapproveOpen by remember {
        mutableStateOf(false)
    }
    var painter : AsyncImagePainter? = null
    if (post.lubangGambar != null) {
        painter = rememberAsyncImagePainter(
            ImageRequest
                .Builder(Context)
                .data(data = post.lubangGambar)
                .build()
        )
    }
    if (approveOpen) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                // If you want to disable that functionality, simply leave this block empty.
                approveOpen = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        approveOpen = false
                        navController.navigate(Congrat.route+ "/${post.id}") {
                            popUpTo(Login.route) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        // close the dialog
                        approveOpen = false
                    }
                ) {
                    Text(text = "Dismiss")
                }
            },
            title = {
                Text(text = "Confirmation")
            },
            text = {
                Column() {
                    Text(text = "Yakin sudah diperbaiki sama pemerintah?")
                    Text(text = "Note:")
                    Text(text = "Jika yakin maka lokasi sama yang diberikan akan tidak bisa dimasukkan lagi selama satu bulan")
                    Text(text = "Dengan cara latitude dan longitude diperhitungkan dan nama daerah nya juga")
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(5.dp),
            backgroundColor = Color.White
        )
    }
    if (disapproveOpen) {
        AlertDialog(
            onDismissRequest = {
                disapproveOpen = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // perform the confirm action and
                        // close the dialog
                        Repo.disApprove(post.id)
                        disapproveOpen = false
                        Toast.makeText(Context, "Successfully change the status and notified the gevernment", Toast.LENGTH_LONG).show()
                    }
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        // close the dialog
                        disapproveOpen = false
                    }
                ) {
                    Text(text = "Dismiss")
                }
            },
            title = {
                Text(text = "Confirmation")
            },
            text = {
                Column() {
                    Text(text = "Apakah benar pemerintah belum membenarkan jalannya?")
                    Text(text = "Note:")
                    Text(text = "Jika yakin maka akun pemerintah akan dikirimkan notifikasi lagi")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(5.dp),
            backgroundColor = Color.White
        )
    }
    LazyColumn{
        item {
            Column(modifier = Modifier.fillMaxSize(0.97f)) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)) {
                    IconButton(onClick = { navController?.navigate(Rumah.route) }) {
                        Image(painter = painterResource(id = R.drawable.back), contentDescription = "Back Icon", modifier = Modifier.fillMaxSize(0.1f))
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    if (post.lubangGambar != null && painter != null) {
                        Image(painter = painter, contentDescription = "Image", modifier = Modifier.fillMaxSize().height(300.dp))
                    }else{
                        Image(painter = painterResource(id = post.imageResource), contentDescription = "Gambar lubang yang default", modifier = Modifier.fillMaxSize(0.5f))
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.mapproto), contentDescription = "Gambar location default sebelum API", modifier = Modifier.fillMaxSize(0.9f))
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "By: ${post.reporter}")
                        Text(text = "")
                        Text(text = post.datePublished, color = Color(0xFF4A4747))
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)) {
                    Text(text = "'${post.description}'",color = Color(0xFF4A4747), fontSize = 20.sp)
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)) {
                    Text(text = "Lokasi:", fontWeight = FontWeight.Bold)
                    Text(text = "${post.address}, ${post.kelurahan}, ${post.kecamatan}, Kota ${post.kota}")
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)) {
                    Text(text = "Government Approval Status:",color = Color(0xFF4A4747))
                    Row( modifier = Modifier.fillMaxWidth()) {
                        if(post.govApproval){
                            Text(text = "Approved")
                            Image(painter = painterResource(id = R.drawable.approve), contentDescription = "Approved Image", modifier = Modifier.fillMaxWidth(0.05f))
                        }else{
                            Text(text = "Pending")
                            Image(painter = painterResource(id = R.drawable.realpending), contentDescription = "Pending Image", modifier = Modifier.fillMaxWidth(0.05f))
                        }
                    }


                }
                if(UserRepo.usernameCookies == post.reporter){
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedButton(onClick = {
                            approveOpen = true
                        },border = BorderStroke(1.dp, Color(0xFF00B0FF)), shape = RoundedCornerShape(20), modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(horizontal = 3.dp, vertical = 5.dp)) {
                            Text(text = "User Approval", color = Color(0xFF00B0FF), fontWeight = FontWeight.ExtraBold)
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedButton(onClick = {
                            disapproveOpen = true
                        },border = BorderStroke(1.dp, Color(0xFFFF1010)), shape = RoundedCornerShape(20), modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(horizontal = 3.dp, vertical = 5.dp)) {
                            Text(text = "User Rejection", color = Color(0xFFFF1010), fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedButton(onClick = {

                    },border = BorderStroke(1.dp, Color(0xFF7F827B)), shape = RoundedCornerShape(20), modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .padding(horizontal = 3.dp, vertical = 5.dp)) {
                        Text(text = "False Post Report", color = Color(0xFF7F827B), fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }

    }
}