package com.example.streettracker

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CaptureScreen(navController: NavHostController){
    if(UserRepo.usernameCookies == ""){
        navController.navigate(Login.route) {
            popUpTo(Login.route) {
                inclusive = true
            }
        }
    }
    val selectedProcess = rememberSaveable {
        mutableStateOf(0)
    }
    val processOne = rememberSaveable { (mutableStateOf(true)) }
    val processTwo = rememberSaveable { (mutableStateOf(false)) }
    val processThree = rememberSaveable { (mutableStateOf(false)) }
    var title by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var description by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var street by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var city by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var kecamatan by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var kelurahan by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        //When the user has selected a photo, its URL is returned here
        photoUri = uri
    }
    val Context = LocalContext.current
    when (selectedProcess.value) {
        0->{
            processOne.value = true
            processTwo.value = false
            processThree.value = false
            }
        1->{
            processOne.value = false
            processTwo.value = true
            processThree.value = false
        }
        2 -> {
            processOne.value = false
            processTwo.value = false
            processThree.value = true
        }
    }
    AnimatedVisibility(visible = processOne.value) {
        Column(modifier = Modifier.fillMaxSize(0.97f)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Lapor Jalan Berlubang", modifier = Modifier.padding(5.dp), fontWeight = FontWeight.ExtraBold, color = Color(0xFF00B0FF), fontSize = 30.sp)
            }
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Black
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.process1), contentDescription = "Process 1", modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(100.dp)
                    .padding(start = 10.dp))
            }
            Text(text = "Judul", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(10.dp, vertical = 15.dp),
                )
            }
                Text(text = "Description", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(10.dp, vertical = 15.dp),
                )
            }
                Text(text = "Nama Jalan", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = street,
                    onValueChange = {
                        street = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(horizontal = 10.dp, vertical = 15.dp),
                )
            }
                Text(text = "Nama Kota", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = city,
                    onValueChange = {
                        city = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(10.dp, vertical = 15.dp),
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = {
                    if(title.text != "" && description.text != "" && street.text != "" && city.text !=""){
                        selectedProcess.value = 1
                    }else{
                        Toast.makeText(Context, "All forms must be filled", Toast.LENGTH_LONG).show()
                    }
                },border = BorderStroke(1.dp, Color(0xFF00B0FF)), shape = RoundedCornerShape(20), modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(horizontal = 0.dp, vertical = 5.dp)) {
                    Text(text = "Next", color = Color(0xFF00B0FF), fontWeight = FontWeight.ExtraBold)
                }
            }
        }
        
    }
    AnimatedVisibility(visible = processTwo.value) {
        Column(modifier = Modifier.fillMaxSize(0.97f)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Lapor Jalan Berlubang", modifier = Modifier.padding(5.dp), fontWeight = FontWeight.ExtraBold, color = Color(0xFF00B0FF), fontSize = 30.sp)
            }
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Black
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.process2), contentDescription = "Process 2", modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(100.dp)
                    .padding(start = 10.dp))
            }
            Text(text = "Nama Kecamatan", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = kecamatan,
                    onValueChange = {
                        kecamatan = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(10.dp, vertical = 15.dp),
                )
            }
            Text(text = "Nama Kelurahan", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = kelurahan,
                    onValueChange = {
                        kelurahan = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(10.dp, vertical = 15.dp),
                )
            }
            Text(text = "Upload Gambar", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp))
            Column {
                if (photoUri != null) {
                   Text(text = "Gambar Berhasil di Uplaod", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = Color(0xFF7DF5C6))
                }

                Button(
                    onClick = {
                        //On button press, launch the photo picker
                        launcher.launch(
                            PickVisualMediaRequest(
                            //Here we request only photos. Change this to .ImageAndVideo if
                            //you want videos too.
                            //Or use .VideoOnly if you only want videos.
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                        )
                    }
                ) {
                    Text("Select Photo Jalan")
                }

            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = {
                    selectedProcess.value = 0
                },border = BorderStroke(1.dp, Color(0xFF986DF2)), shape = RoundedCornerShape(20), modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(horizontal = 0.dp, vertical = 5.dp)) {
                    Text(text = "Previous", color = Color(0xFF986DF2), fontWeight = FontWeight.ExtraBold)
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = {
                    if(kecamatan.text != "" && kelurahan.text != "" && photoUri != null){
                        selectedProcess.value = 2
                    }else{
                        Toast.makeText(Context, "All forms must be filled", Toast.LENGTH_LONG).show()
                    }
                },border = BorderStroke(1.dp, Color(0xFF00B0FF)), shape = RoundedCornerShape(20), modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(horizontal = 0.dp, vertical = 5.dp)) {
                    Text(text = "Next", color = Color(0xFF00B0FF), fontWeight = FontWeight.ExtraBold)
                }
            }
        }
    }
    AnimatedVisibility(visible = processThree.value) {
        LazyColumn(){
            item {
                Column(modifier = Modifier
                    .fillMaxSize(0.97f)) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Lapor Jalan Berlubang", modifier = Modifier.padding(5.dp), fontWeight = FontWeight.ExtraBold, color = Color(0xFF00B0FF), fontSize = 30.sp)
                    }
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp,
                        color = Color.Black
                    )
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Image(painter = painterResource(id = R.drawable.process3), contentDescription = "Process 2", modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(100.dp)
                            .padding(start = 10.dp))
                    }
                    Text(text = "Konfirmasi Gambar", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                    if (photoUri != null) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                            //Use Coil to display the selected image
                            val painter = rememberAsyncImagePainter(
                                ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(data = photoUri)
                                    .build()
                            )
                            Image(painter = painter, contentDescription = "Image", modifier = Modifier.fillMaxSize().height(300.dp))
                        }
                    }
                    Text(text = "NOTE: Gambar dibawah berupa experimental (karena API google map berbayar)")
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = R.drawable.mapproto), contentDescription = "Gambar location default sebelum API", modifier = Modifier.fillMaxSize(0.9f))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = {
                            selectedProcess.value = 1
                        },border = BorderStroke(1.dp, Color(0xFF986DF2)), shape = RoundedCornerShape(20), modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .padding(horizontal = 0.dp, vertical = 5.dp)) {
                            Text(text = "Previous", color = Color(0xFF986DF2), fontWeight = FontWeight.ExtraBold)
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = {
                            val sdf = SimpleDateFormat("dd/M/yyyy")
                            val currentDate = sdf.format(Date())
                            var tempDatum = Datum(
                                Repo.data.last().id+1,
                                title.text,
                                description.text,
                                UserRepo.usernameCookies,
                                street.text,
                                city.text,
                                kecamatan.text,
                                kelurahan.text,
                                currentDate,
                                R.drawable.lubang,
                                0,
                                photoUri,
                                false
                            )
                            Repo.insertContent(tempDatum)
                            navController.navigate(Rumah.route) {
                                popUpTo(Login.route) {
                                    inclusive = true
                                }
                            }
                        },border = BorderStroke(1.dp, Color(0xFF00B0FF)), shape = RoundedCornerShape(20), modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .padding(horizontal = 0.dp, vertical = 5.dp)) {
                            Text(text = "Submut", color = Color(0xFF00B0FF), fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
            }
        }

    }
}
