package com.example.streettracker

import android.Manifest
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavHostController,data:List<Datum> = listOf()){
    val Context = LocalContext.current
    val radioOptions = mutableListOf("All Post")
    val filterState = rememberSaveable { (mutableStateOf(false)) }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    if(UserRepo.usernameCookies == ""){
        navController.navigate(Login.route) {
            popUpTo(Login.route) {
                inclusive = true
            }
        }
    }
    val currUser = UserRepo.getUserbyUsername(UserRepo.usernameCookies)
    if(currUser?.Kepemerintahan != "Admin" && currUser?.Kepemerintahan != "User" ){
        if(currUser?.Kepemerintahan != null){
            radioOptions.add(currUser.Kepemerintahan)
        }
    }else{
        radioOptions.add("My Post")
    }
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val brush1 = Brush.horizontalGradient(listOf(Color(0xFFC0E9EF),Color(0xFFD0A4FF)))
    Column {
        Column() {
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {
                Text(text = "ST", modifier = Modifier
                    .textBrush(brush1)
                    .padding(start = 10.dp), fontSize = 40.sp, fontFamily = FontFamily.Cursive)
                Text(text = "")
                IconButton(onClick = { filterState.value = !filterState.value }) {
                    Image(painter = painterResource(id = R.drawable.filter), contentDescription = "Filter", modifier = Modifier
                        .fillMaxSize(0.12f)
                        .padding(end = 10.dp))
                }
            }
        }
        Column() {
            androidx.compose.animation.AnimatedVisibility(visible = filterState.value) {
                Column {
                    // below line is use to set data to
                    // each radio button in columns.
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                // using modifier to add max
                                // width to our radio button.
                                .fillMaxWidth()
                                // below method is use to add
                                // selectable to our radio button.
                                .selectable(
                                    // this method is called when
                                    // radio button is selected.
                                    selected = (text == selectedOption),
                                    // below method is called on
                                    // clicking of radio button.
                                    onClick = { onOptionSelected(text) }
                                )
                                // below line is use to add
                                // padding to radio button.
                                .padding(horizontal = 16.dp)
                        ) {
                            // below line is use to get context.
                            val context = Context

                            // below line is use to
                            // generate radio button
                            RadioButton(
                                // inside this method we are
                                // adding selected with a option.
                                selected = (text == selectedOption),
                                modifier = Modifier.padding(all = Dp(value = 8F)),
                                onClick = {
                                    // inside on click method we are setting a
                                    // selected option of our radio buttons.
                                    onOptionSelected(text)

                                    // after clicking a radio button
                                    // we are displaying a toast message.
                                    Toast.makeText(context, selectedOption, Toast.LENGTH_LONG).show()
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFF00B0FF),
                                    unselectedColor = Color(0xFF986DF2),
                                )
                            )
                            Text(
                                text = text,
                                modifier = Modifier.padding(start = 16.dp, top = 5.dp)
                            )
                        }
                    }
                }
            }
        }
            Column(
                modifier = Modifier.fillMaxSize(0.97f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn {
                    when (selectedOption) {
                        "All Post" -> {
                            itemsIndexed(data) { _, datum ->
                                ContentAll(navController, datum = datum, permissionState)
                            }
                        }
                        "My Post" -> {
                            val filteredData = data.filter {datum -> UserRepo.usernameCookies == datum.reporter }
                            itemsIndexed(filteredData) { _, datum ->
                                ContentAll(navController, datum = datum, permissionState)
                            }
                        }
                        else -> {
                            val filteredData = data.filter {datum -> selectedOption == datum.kota }
                            itemsIndexed(filteredData) { _, datum ->
                                ContentAll(navController, datum = datum, permissionState)
                            }
                        }
                    }
                }
            }
        }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun ContentAll(navController: NavHostController? = null,datum:Datum,permissionState:PermissionState){
    Card(modifier = Modifier
        .fillMaxSize(0.9f)
        .padding(5.dp), onClick = {
        permissionState.launchPermissionRequest()
        PostDetails.mapPermission = permissionState
        navController?.navigate(PostDetails.route + "/${datum.id}")}) {
        Column(verticalArrangement = Arrangement.Top) {
            Column(modifier = Modifier.fillMaxSize(1f)) {
                if (datum.lubangGambar != null) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                        //Use Coil to display the selected image
                        val painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = datum.lubangGambar)
                                .build()
                        )
                        Image(painter = painter, contentDescription = "Image", modifier = Modifier.size(width = 330.dp,200.dp))
                    }
                }else{
                    Image(painter = painterResource(id = datum.imageResource), contentDescription = datum.title, modifier = Modifier.size(width = 330.dp,200.dp))
                }

            }
            Column(modifier = Modifier.fillMaxSize(0.7f)) {
                Text(text = datum.title,modifier = Modifier.padding(8.dp,3.dp), fontWeight = FontWeight.Bold)
                Column(modifier = Modifier.padding(2.dp)) {
                    Row() {
                        Image(painter = painterResource(id = R.drawable.user), contentDescription = "Pelapor", modifier = Modifier.fillMaxSize(0.2f))
                        Text(text = datum.reporter, modifier = Modifier.padding(start = 2.dp, top = 10.dp),)
                    }
                }
                Column(modifier = Modifier.padding(2.dp)) {
                    Row() {
                        Image(painter = painterResource(id = R.drawable.location), contentDescription = "Location", modifier = Modifier.fillMaxSize(0.2f))
                        Text(text = datum.address, modifier = Modifier.padding(start = 2.dp, top = 10.dp))
                    }
                }
            }
        }

    }
}